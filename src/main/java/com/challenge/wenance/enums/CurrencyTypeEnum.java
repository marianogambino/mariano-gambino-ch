package com.challenge.wenance.enums;

public enum CurrencyTypeEnum {
    DAIARS("daiars"),
    BTCARS("btcars"),
    DAIUSD("daiusd"),
    ETHARS("ethars"),
    BTCDAI("btcdai"),
    ETHDAI("ethdai");

    private String type;


    CurrencyTypeEnum(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public static CurrencyTypeEnum fromString(String text) {
        for (CurrencyTypeEnum b : CurrencyTypeEnum.values()) {
            if (b.type.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
