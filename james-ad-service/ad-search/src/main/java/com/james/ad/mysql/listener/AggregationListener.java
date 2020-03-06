package com.james.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.james.ad.mysql.TemplateHolder;
import com.james.ad.mysql.dto.BinlogRowData;
import com.james.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;

    private Map<String, IListener> listenerMap = new HashMap<>();

    private final TemplateHolder templateHolder;

    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    public void register(String _dbName, String _tableName, IListener iListener){
        log.info("register : {}-{}", _dbName, _tableName);
        this.listenerMap.put(genKey(_dbName,_tableName), iListener);
    }

    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();
        log.debug("event type: {}", type);
        if (type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }
        if (type != EventType.EXT_UPDATE_ROWS && type != EventType.WRITE_ROWS && type != EventType.DELETE_ROWS) {
            return;
        }

        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        String key = genKey(dbName,tableName);
        IListener listener = this.listenerMap.get(key);
        if (listener == null) {
            log.debug("skip {}", key);
            return;
        }

        log.info("trigger event: {}", type.name());
        //event data to binlog row data
        try{
            BinlogRowData rowData = buildRowData(event.getData());
            if (rowData == null) {
                return;
            }
            rowData.setEventType(type);
            listener.onEvent(rowData);
        }catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        } finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    private List<Serializable[]> getAfterValues(EventData eventData){
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }
        //collect values in updateRowsEventData's entries
        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream().map(Map.Entry :: getValue).collect(Collectors.toList());
        }
        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData)eventData).getRows();
        }
        return Collections.emptyList();
    }

    private BinlogRowData buildRowData(EventData eventData){
        TableTemplate table = templateHolder.getTable(tableName);
        if (table == null) {
            log.warn("table {} not found", tableName);
            return null;
        }
        List<Map<String,String>> afterMapList = new ArrayList<>();
        for (Serializable[] after : getAfterValues(eventData)){
            Map<String,String> afterMap = new HashMap<>();
            int colLen = after.length;
            for (int ix = 0; ix < colLen; ix++) {
                String colName = table.getPosMap().get(ix);
                if (colName == null) {
                    log.debug("ignore position: {}",ix);
                    continue;
                }
                String colValue = after[ix].toString();
                afterMap.put(colName,colValue);
            }
            afterMapList.add(afterMap);
        }
        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);
        return rowData;
    }
}
