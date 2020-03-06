package com.james.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;
/*
Write----------
WriteRowsEventData{tableId=107, includedColumns={0, 1, 2}, rows=[
    [15, 10, test3]
]}
Update----------
UpdateRowsEventData{tableId=107, includedColumnsBeforeUpdate={0, 1, 2}, includedColumns={0, 1, 2}, rows=[
    {before=[11, 10, test3], after=[11, 10, test5]},
    {before=[14, 10, test3], after=[14, 10, test5]},
    {before=[15, 10, test3], after=[15, 10, test5]}
]}
 */
public class BinlogServiceTest {
    public static void main(String[] args) throws IOException {
        BinaryLogClient client = new BinaryLogClient(
                "127.0.0.1",
                3306,
                "root",
                "Zyx940505+"
        );
        client.setBinlogFilename("binlog.000037");
//        client.setBinlogPosition();
        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update----------");
                System.out.println(data.toString());
            }
            else if (data instanceof WriteRowsEventData){
                System.out.println("Write----------");
                System.out.println(data.toString());
            }
            else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete----------");
                System.out.println(data.toString());
            }
        });
        client.connect();
    }
}
