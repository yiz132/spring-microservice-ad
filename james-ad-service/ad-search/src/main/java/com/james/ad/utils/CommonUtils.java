package com.james.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

public class CommonUtils {

    public static <K, V> V getOrCreate(K key, Map<K,V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

    public static String stringConcat(String...args) {
        StringBuilder res = new StringBuilder();
        for (String arg : args) {
            res.append(arg);
            res.append("-");
        }
        res.deleteCharAt(res.length()-1);
        return res.toString();
    }
}
