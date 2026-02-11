package com.haseeb.assetledger.Model;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Asset {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AssetType type;

    private BigDecimal amount;

    public Asset() {
    }

    public Asset(Long id, String name, AssetType type, BigDecimal amount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AssetType getType() {
        return type;
    }

    public void setType(AssetType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }
}


