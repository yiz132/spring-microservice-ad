package com.james.ad.constant;

import lombok.Getter;

@Getter
public enum CreativeType {
    IMAGE(1,"image"),
    VIDEO(2, "video"),
    TEXT(3,"text");

    private int type;
    private String desc;

    CreativeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
