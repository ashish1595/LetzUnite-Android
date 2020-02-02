package com.letzunite.letzunite.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Deven Singh on 18 Jun, 2018.
 */
public enum  BloodType {

    DEFAULT("ALL"),A_POSITIVE("A+"),A_NEGATIVE("A-"), B_POSITIVE("B+"), B_NEGATIVE("B-"),
    O_POSITIVE("O+"),O_NEGATIVE("O-"), AB_POSITIVE("AB+"), AB_NEGATIVE("AB-");

    private String bloodType;
    private static Map map = new HashMap<>();

    BloodType(String feedType){
        this.bloodType=feedType;
    }

    public String getBloodType(){
        return bloodType;
    }

    static {
        for (BloodType blood : BloodType.values()) {
            map.put(blood.bloodType, blood);
        }
    }

    public static BloodType getType(String bloodType) {
        return (BloodType) map.get(bloodType);
    }
}
