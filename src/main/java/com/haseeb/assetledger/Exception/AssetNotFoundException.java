package com.haseeb.assetledger.Exception;

public class AssetNotFoundException extends RuntimeException {

    public AssetNotFoundException(Long id) {
        super("Asset not found with id: " + id);
    }
}