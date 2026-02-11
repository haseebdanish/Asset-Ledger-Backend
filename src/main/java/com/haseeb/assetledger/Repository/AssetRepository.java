package com.haseeb.assetledger.Repository;

import com.haseeb.assetledger.Model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("SELECT COALESCE(SUM(a.amount), 0) FROM Asset a")
    BigDecimal calculateTotalAssets();
}
