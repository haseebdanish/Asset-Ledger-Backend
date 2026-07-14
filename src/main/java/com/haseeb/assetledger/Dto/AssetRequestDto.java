package com.haseeb.assetledger.Dto;

import com.haseeb.assetledger.Model.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AssetRequestDto(

        @NotBlank(message = "Asset name is required")
        String assetName,

        @NotNull(message = "Asset type is required")
        AssetType assetType,

        @NotNull(message = "Quantity is required")
        BigDecimal quantity,

        @NotNull(message = "Invested amount is required")
        BigDecimal investedAmount
) {
}
