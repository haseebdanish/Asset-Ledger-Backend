package com.haseeb.assetledger.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        indexes = {
                @Index(
                        name = "idx_asset_user_type",
                        columnList = "user_id, asset_type"
                ),
                @Index(
                        name = "idx_asset_user_name_type",
                        columnList = "user_id, asset_name, asset_type"
                )
        }
)
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String assetName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    @Column(nullable = false, precision = 19, scale = 2)
    @NotNull
    @PositiveOrZero
    private BigDecimal investedAmount;

    @Column(nullable = false, precision = 30, scale = 12)
    @NotNull
    @Positive
    private BigDecimal quantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Asset() {
    }

    public Asset(Long id, String assetName, AssetType assetType, BigDecimal investedAmount, BigDecimal quantity, LocalDateTime createdAt, LocalDateTime updatedAt, User user) {
        this.id = id;
        this.assetName = assetName;
        this.assetType = assetType;
        this.investedAmount = investedAmount;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public BigDecimal getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(BigDecimal investedAmount) {
        this.investedAmount = investedAmount;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", assetName='" + assetName + '\'' +
                ", assetType=" + assetType +
                ", investedAmount=" + investedAmount +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", user=" + user +
                '}';
    }
}


