package com.haseeb.assetledger.Dto;

import com.haseeb.assetledger.Model.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record AssetRequestDto(

        @NotBlank(message = "Asset name is required")
        String assetName,

        @NotNull(message = "Asset type is required")
        AssetType assetType,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than zero")
        BigDecimal quantity,

        @NotNull(message = "Invested amount is required")
        @PositiveOrZero(message = "Invested amount cannot be negative")
        BigDecimal investedAmount

) {
}
