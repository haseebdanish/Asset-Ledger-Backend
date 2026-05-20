package com.haseeb.assetledger.Model;

public enum AssetType {
    MUTUAL_FUND,
    STOCK,
    BANK,
    CRYPTO,
    GOLD,
    REAL_ESTATE;

    public static AssetType fromString(String value){
        return AssetType.valueOf(
                value.trim()
                        .toUpperCase()
                        .replace('_',' ')
        );
    }
}

