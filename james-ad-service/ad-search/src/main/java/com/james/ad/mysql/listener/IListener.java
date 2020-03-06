package com.james.ad.mysql.listener;

import com.james.ad.mysql.dto.BinlogRowData;

public interface IListener {

    void register();

    void onEvent(BinlogRowData eventData);
}
