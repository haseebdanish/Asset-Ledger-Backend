package com.haseeb.assetledger.Controller;

import com.haseeb.assetledger.Model.Asset;
import com.haseeb.assetledger.Service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class AssetController {

    public AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }


    @PostMapping("/{userid}/assets")
    public ResponseEntity<Asset> addOrUpdateAsset(
            @PathVariable Long userid,
            @RequestBody Asset asset
            ) {
        Asset savedAsset = assetService.addOrUpdateAsset(userid, asset);
        return ResponseEntity.ok(savedAsset);
    }

    @GetMapping("/{userid}/assets")
    public ResponseEntity<List<Asset>> getUserAssets(
            @PathVariable Long userid
    ) {
        List<Asset> assets = assetService.getUserAssets(userid);
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/{userid}/networth")
    public ResponseEntity<BigDecimal> getNetworth(
            @PathVariable Long userid
    ) {
        BigDecimal networth = assetService.getNetworth(userid);
        return ResponseEntity.ok(networth);
    }

}
