package com.haseeb.assetledger.Controller;

import com.haseeb.assetledger.Model.Asset;
import com.haseeb.assetledger.Service.AssetService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AssetController {

    public AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }


    @PostMapping("/assets")
    public Asset saveAsset(
            @Valid @RequestBody Asset asset
    ) {
        return assetService.saveAsset(asset);
    }

    @GetMapping("/assets/total")
    public BigDecimal getTotalAsset(){
        return assetService.getTotalAsset();
    }
}
