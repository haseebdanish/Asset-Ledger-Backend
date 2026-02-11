package com.haseeb.assetledger.Dto;

import java.math.BigDecimal;

public record FinanceDto(
        BigDecimal totalAssets,
        BigDecimal netWorth
) {
}
