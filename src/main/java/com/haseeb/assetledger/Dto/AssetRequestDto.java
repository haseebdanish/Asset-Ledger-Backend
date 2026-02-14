package com.haseeb.assetledger.Dto;

import com.haseeb.assetledger.Model.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AssetRequestDto(

        @NotBlank
        String assetName,
        @NotBlank
        AssetType assetType,
        @NotNull
        BigDecimal investedAmount,
        @NotNull
        BigDecimal quantity
) {
}
