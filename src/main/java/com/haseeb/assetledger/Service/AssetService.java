package com.haseeb.assetledger.Service;

import com.haseeb.assetledger.Dto.AssetRequestDto;
import com.haseeb.assetledger.Dto.AssetResponseDto;
import com.haseeb.assetledger.Model.Asset;
import com.haseeb.assetledger.Model.User;
import com.haseeb.assetledger.Repository.AssetRepository;
import com.haseeb.assetledger.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssetService {

    private AssetRepository assetRepository;
    private UserRepository userRepository;

    public AssetService(AssetRepository assetRepository, UserRepository userRepository) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    public AssetResponseDto addOrUpdateAsset(Long userId, AssetRequestDto request) {

        //1:Check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //2:Check if user Already has that same asset
        Asset asset = assetRepository
                .findByUserAndAssetName(user, request.assetName())
                .map(existingAsset -> {
                    //3: If exists update Value
                    existingAsset.setQuantity(
                            existingAsset.getQuantity().add(request.quantity())
                    );

                    existingAsset.setInvestedAmount(
                            existingAsset.getInvestedAmount().add(request.investedAmount())
                    );

                    existingAsset.setUpdatedAt(LocalDateTime.now());

                    return existingAsset;

                })
                .orElseGet(() -> {
                    //If no asset is there create new asset
                    Asset newAsset = new Asset();
                    newAsset.setUser(user);
                    newAsset.setAssetName(request.assetName());
                    newAsset.setAssetType(request.assetType());
                    newAsset.setQuantity(request.quantity());
                    newAsset.setInvestedAmount(request.investedAmount());
                    newAsset.setCreatedAt(LocalDateTime.now());
                    newAsset.setUpdatedAt(LocalDateTime.now());

                    return newAsset;

                });
        Asset saved = assetRepository.save(asset);

        return new AssetResponseDto(
                saved.getId(),
                saved.getAssetName(),
                saved.getAssetType(),
                saved.getQuantity(),
                saved.getInvestedAmount()
        );
    }

    public BigDecimal getNetworth(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return assetRepository.getTotalInvestedByUser(user);
    }

    public List<AssetResponseDto> getUserAssets(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return assetRepository.findByUser(user)
                .stream()
                .map(asset -> new AssetResponseDto(
                        asset.getId(),
                        asset.getAssetName(),
                        asset.getAssetType(),
                        asset.getQuantity(),
                        asset.getInvestedAmount()
                ))
                .toList();
    }

}
