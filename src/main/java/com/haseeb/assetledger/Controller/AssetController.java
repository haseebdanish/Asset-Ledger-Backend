package com.haseeb.assetledger.Controller;

import com.haseeb.assetledger.Dto.*;
import com.haseeb.assetledger.Model.AssetType;
import com.haseeb.assetledger.Service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping("")
    public ResponseEntity<AssetResponseDto> addOrUpdateAsset(
            Authentication authentication,
            @Valid @RequestBody AssetRequestDto request
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                assetService.addOrUpdateAsset(email, request)
        );
    }

    @GetMapping("")
    public ResponseEntity<List<AssetResponseDto>> getUserAssets(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                assetService.getUserAssets(authentication.getName())
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

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDto> getAssetById(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                assetService.getAssetById(authentication.getName(), id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetResponseDto> updateAsset(
            @PathVariable Long id,
            @RequestBody @Valid AssetRequestDto request,
            Authentication authentication) {

        return ResponseEntity.ok(
                assetService.updateAsset(authentication.getName(), id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(
            @PathVariable Long id,
            Authentication authentication) {

        assetService.deleteAsset(authentication.getName(), id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<PortfolioSummaryDto> getSummary(
            Authentication authentication){

        return ResponseEntity.ok(
                assetService.getPortfolioSummary(authentication.getName())
        );
    }

    @GetMapping("/allocation")
    public ResponseEntity<List<PortfolioAllocationDto>> getAllocation(
            Authentication authentication){

        return ResponseEntity.ok(
                assetService.getAllocation(authentication.getName())
        );
    }

    // Sample request to hit this endpoint: GET /assets/page?page=0&size=5
    @GetMapping("/page")
    public ResponseEntity<Page<AssetResponseDto>> getAssetsPaginated(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            Authentication authentication) {

        return ResponseEntity.ok(
                assetService.getUserAssets(
                        authentication.getName(),
                        page,
                        size
                )
        );
    }

    @GetMapping("/type/{assetType}")
    public ResponseEntity<List<AssetResponseDto>> getAssetsByType(

            @PathVariable AssetType assetType,

            Authentication authentication) {

        return ResponseEntity.ok(

                assetService.getAssetsByType(

                        authentication.getName(),

                        assetType

                )

        );

    }

    @GetMapping("/search")
    public ResponseEntity<List<AssetResponseDto>> searchAssets(
            @RequestParam String keyword,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                assetService.searchAssets(
                        authentication.getName(),
                        keyword
                )
        );
    }

    @GetMapping("/analytics")
    public ResponseEntity<PortfolioAnalyticsDto> getAnalytics(
            Authentication authentication) {

        return ResponseEntity.ok(
                assetService.getAnalytics(authentication.getName())
        );
    }

}