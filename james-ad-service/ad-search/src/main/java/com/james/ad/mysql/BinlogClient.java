package com.james.ad.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.james.ad.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class BinlogClient {

    private BinaryLogClient client;

    private final BinlogConfig config;
    private final AggregationListener aggregationListener;

    public BinlogClient(BinlogConfig config, AggregationListener aggregationListener) {
        this.config = config;
        this.aggregationListener = aggregationListener;
    }

    public void connect(){
        new Thread(() -> {
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (!StringUtils.isEmpty(config.getBinlogName()) && !config.getPosition().equals(-1L)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }
            client.registerEventListener(aggregationListener);
        }).start();
        try{
            log.info("connecting to mysql start");
            client.connect();
            log.info("connecting to mysql done");
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void close(){
        try{
            client.disconnect();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
