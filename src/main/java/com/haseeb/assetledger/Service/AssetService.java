package com.haseeb.assetledger.Service;

import com.haseeb.assetledger.Dto.AssetRequestDto;
import com.haseeb.assetledger.Dto.AssetResponseDto;
import com.haseeb.assetledger.Dto.PortfolioSummaryDto;
import com.haseeb.assetledger.Exception.AssetNotFoundException;
import com.haseeb.assetledger.Exception.UserNotFoundException;
import com.haseeb.assetledger.Model.Asset;
import com.haseeb.assetledger.Model.AssetType;
import com.haseeb.assetledger.Model.User;
import com.haseeb.assetledger.Repository.AssetRepository;
import com.haseeb.assetledger.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssetService {


    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    public AssetService(AssetRepository assetRepository, UserRepository userRepository) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Transactional
    public AssetResponseDto addOrUpdateAsset(String email, AssetRequestDto request) {

        //1:Check if user exists
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        //2:Check if user Already has that same asset
        Asset asset = assetRepository
                .findByUserAndAssetNameAndAssetType(
                        user,
                        request.assetName(),
                        request.assetType()
                )
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

    public BigDecimal getNetworth(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        BigDecimal total = assetRepository.getTotalInvestedByUser(user);

        return total != null ? total : BigDecimal.ZERO;
    }

    public List<AssetResponseDto> getUserAssets(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

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

    public AssetResponseDto getAssetById(String email, Long assetId) {

        User user = getUserByEmail(email);

        Asset asset = assetRepository.findByIdAndUser(assetId, user)
                .orElseThrow(() -> new AssetNotFoundException(assetId));

        return new AssetResponseDto(
                asset.getId(),
                asset.getAssetName(),
                asset.getAssetType(),
                asset.getQuantity(),
                asset.getInvestedAmount()
        );
    }

    @Transactional
    public AssetResponseDto updateAsset(
            String email,
            Long assetId,
            AssetRequestDto request) {

        User user = getUserByEmail(email);

        Asset asset = assetRepository.findByIdAndUser(assetId, user)
                .orElseThrow(() -> new AssetNotFoundException(assetId));

        asset.setAssetName(request.assetName());
        asset.setAssetType(request.assetType());
        asset.setQuantity(request.quantity());
        asset.setInvestedAmount(request.investedAmount());
        asset.setUpdatedAt(LocalDateTime.now());

        Asset updated = assetRepository.save(asset);

        return new AssetResponseDto(
                updated.getId(),
                updated.getAssetName(),
                updated.getAssetType(),
                updated.getQuantity(),
                updated.getInvestedAmount()
        );
    }

    @Transactional
    public void deleteAsset(String email, Long assetId) {

        User user = getUserByEmail(email);

        Asset asset = assetRepository.findByIdAndUser(assetId, user)
                .orElseThrow(() -> new AssetNotFoundException(assetId));

        assetRepository.delete(asset);
    }

    public PortfolioSummaryDto getPortfolioSummary(String email){

        User user = getUserByEmail(email);

        return new PortfolioSummaryDto(

                assetRepository.getTotalInvestedByUser(user),

                assetRepository.countByUser(user),

                assetRepository.getTotalInvestmentByType(user, AssetType.STOCK),

                assetRepository.getTotalInvestmentByType(user, AssetType.MUTUAL_FUND),

                assetRepository.getTotalInvestmentByType(user, AssetType.CRYPTO),

                assetRepository.getTotalInvestmentByType(user, AssetType.GOLD)

        );
    }

}
