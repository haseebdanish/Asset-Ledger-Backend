package com.haseeb.assetledger.Dto;

import com.haseeb.assetledger.Model.AssetType;

import java.math.BigDecimal;

public record AssetResponseDto(

        Long id,
        String assetName,
        AssetType assetType,
        BigDecimal quantity,
        BigDecimal investedAmount
) {
}
