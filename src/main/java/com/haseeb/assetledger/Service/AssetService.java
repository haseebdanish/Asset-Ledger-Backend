package com.haseeb.assetledger.Service;

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

    public Asset addOrUpdateAsset(Long userId, Asset requestAsset) {

        //1:Check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //2:Check if user Already has that same asset
        return assetRepository
                .findByUserAndAssetName(user, requestAsset.getAssetName())
                .map(existingAsset -> {
                    //3: If exists update Value
                    existingAsset.setQuantity(
                            existingAsset.getQuantity().add(requestAsset.getQuantity())
                    );

                    existingAsset.setInvestedAmount(
                            existingAsset.getInvestedAmount().add(requestAsset.getInvestedAmount())
                    );

                    existingAsset.setUpdatedAt(LocalDateTime.now());

                    return assetRepository.save(existingAsset);

                })
                .orElseGet(() -> {
                    //If no asset is there create new asset
                    requestAsset.setUser(user);
                    requestAsset.setCreatedAt(LocalDateTime.now());
                    requestAsset.setUpdatedAt(LocalDateTime.now());

                    return assetRepository.save(requestAsset);

                });
    }

    public BigDecimal getNetworth(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return assetRepository.getTotalInvestedByUser(user);
    }

    public List<Asset> getUserAssets(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not Found"));

        return assetRepository.findByUser(user);
    }

}
