package com.haseeb.assetledger.Service;

import com.haseeb.assetledger.Model.Asset;
import com.haseeb.assetledger.Repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AssetService {

    public AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public Asset saveAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    public BigDecimal getTotalAsset() {
        return assetRepository.calculateTotalAssets();
    }


}
