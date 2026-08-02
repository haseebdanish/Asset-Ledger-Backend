package com.haseeb.assetledger.Repository;

import com.haseeb.assetledger.Model.Asset;
import com.haseeb.assetledger.Model.AssetType;
import com.haseeb.assetledger.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    // Find asset by user and asset name
    Optional<Asset> findByUserAndAssetNameAndAssetType(
            User user,
            String assetName,
            AssetType assetType
    );

    // Find asset by ID and user (used for authorization)
    Optional<Asset> findByIdAndUser(Long id, User user);

    // List all assets of a user
    List<Asset> findByUser(User user);

    // Total invested amount (Net Worth)
    @Query("""
            SELECT COALESCE(SUM(a.investedAmount), 0)
            FROM Asset a
            WHERE a.user = :user
            """)
    BigDecimal getTotalInvestedByUser(@Param("user") User user);

    // Total number of assets
    Long countByUser(User user);

    // Total investment by asset type
    @Query("""
            SELECT COALESCE(SUM(a.investedAmount), 0)
            FROM Asset a
            WHERE a.user = :user
            AND a.assetType = :assetType
            """)
    BigDecimal getTotalInvestmentByType(
            @Param("user") User user,
            @Param("assetType") AssetType assetType
    );

    Page<Asset> findByUser(User user, Pageable pageable);

    List<Asset> findByUserAndAssetType(User user, AssetType assetType);
}
