package com.letzunite.letzunite.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Deven Singh on 16 Jun, 2018.
 */
public enum FeedsType {

    DEFAULT_FEEDS(0),BLOOD_REQUEST_FEEDS(1), CHILD_CARE_FEEDS(2), GENERAL_FEEDS(3);

    private int feedType;
    private static Map map = new HashMap<>();

    FeedsType(int feedType){
        this.feedType=feedType;
    }

    public int getFeedType(){
        return feedType;
    }

    static {
        for (FeedsType pageType : FeedsType.values()) {
            map.put(pageType.feedType, pageType);
        }
    }

    public static FeedsType getType(int FeedsType) {
        return (FeedsType) map.get(FeedsType);
    }
}
