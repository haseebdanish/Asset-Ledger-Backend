package com.haseeb.assetledger.Controller;

import com.haseeb.assetledger.Dto.AssetRequestDto;
import com.haseeb.assetledger.Dto.AssetResponseDto;
import com.haseeb.assetledger.Model.Asset;
import com.haseeb.assetledger.Service.AssetService;
import jakarta.validation.Valid;
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
    public ResponseEntity<AssetResponseDto> addOrUpdateAsset(
            @PathVariable Long userid,
            @Valid @RequestBody AssetRequestDto request
            ) {
        return ResponseEntity.ok(assetService.addOrUpdateAsset(userid, request));
    }

    @GetMapping("/{userid}/assets")
    public ResponseEntity<List<AssetResponseDto>> getUserAssets(
            @PathVariable Long userid
    ) {
        return ResponseEntity.ok(assetService.getUserAssets(userid));
    }

    @GetMapping("/{userid}/networth")
    public ResponseEntity<BigDecimal> getNetworth(
            @PathVariable Long userid
    ) {
        BigDecimal networth = assetService.getNetworth(userid);
        return ResponseEntity.ok(networth);
    }

}
