package com.haseeb.assetledger.Controller;

import com.haseeb.assetledger.Dto.AssetRequestDto;
import com.haseeb.assetledger.Dto.AssetResponseDto;
import com.haseeb.assetledger.Service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping("/assets")
    public ResponseEntity<AssetResponseDto> addOrUpdateAsset(
            Authentication authentication,
            @Valid @RequestBody AssetRequestDto request
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                assetService.addOrUpdateAsset(email, request)
        );
    }

    @GetMapping("/assets")
    public ResponseEntity<List<AssetResponseDto>> getUserAssets(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                assetService.getUserAssets(email)
        );
    }

    @GetMapping("/networth")
    public ResponseEntity<BigDecimal> getNetworth(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                assetService.getNetworth(email)
        );
    }
}