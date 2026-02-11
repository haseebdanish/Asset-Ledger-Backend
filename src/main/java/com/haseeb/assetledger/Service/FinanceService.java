package com.haseeb.assetledger.Service;

import com.haseeb.assetledger.Repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FinanceService {

    public AssetRepository assetRepository;

    public FinanceService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public BigDecimal getTotalAssets() {
        return assetRepository.calculateTotalAssets();
    }

    public BigDecimal getNetworth() {
        BigDecimal assets = getTotalAssets();
        return assets;
    }
}
