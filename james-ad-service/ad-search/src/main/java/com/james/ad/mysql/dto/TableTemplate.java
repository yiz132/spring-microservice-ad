package com.james.ad.mysql.dto;

import com.james.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.map.HashedMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {

    private String tableName;
    private String level;

    private Map<OpType, List<String>> opTypeFieldSetMap = new HashedMap<>();

    // index to index name (i.e. 0, 1 ,2 ,3 -> name)
    private Map<Integer, String> posMap = new HashMap<>();
}
