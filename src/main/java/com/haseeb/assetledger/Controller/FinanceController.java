package com.haseeb.assetledger.Controller;


import com.haseeb.assetledger.Repository.AssetRepository;
import com.haseeb.assetledger.Service.AssetService;
import com.haseeb.assetledger.Service.FinanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class FinanceController {

    public FinanceService financeService;
    public AssetRepository assetRepository;


    public FinanceController(FinanceService financeService, AssetRepository assetRepository) {
        this.financeService = financeService;
        this.assetRepository = assetRepository;
    }

    @GetMapping("/networth")
    public BigDecimal getNetworth() {
        return financeService.getNetworth();
    }
}
