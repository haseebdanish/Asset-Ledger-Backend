package com.haseeb.assetledger.Repository;

import com.haseeb.assetledger.Model.Asset;
import com.haseeb.assetledger.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    //Find asset by user and assetName
    Optional<Asset> findByUserAndAssetName(User user, String assetName);

    //To List All Assets Of a User
    List<Asset> findByUser(User user);

    //NetWorth
    @Query("SELECT COALESCE(SUM(a.investedAmount), 0) FROM Asset a WHERE a.user = :user")
    BigDecimal getTotalInvestedByUser(@Param("user") User user);

}
