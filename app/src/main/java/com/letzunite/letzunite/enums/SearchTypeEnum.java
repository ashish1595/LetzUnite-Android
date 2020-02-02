package com.letzunite.letzunite.enums;

/**
 * Created by Deven Singh on 01 Aug, 2018.
 */
public enum SearchTypeEnum {

    ALL(0), DONOR(1), REQUESTER(2), BLOOD_BANK(3);

    private final int value;

    SearchTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
