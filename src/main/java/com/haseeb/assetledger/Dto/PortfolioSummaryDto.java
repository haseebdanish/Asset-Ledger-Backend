package com.haseeb.assetledger.Dto;

import java.math.BigDecimal;

public record PortfolioSummaryDto(
        BigDecimal totalInvestment,

        Long totalAssets,

        BigDecimal stockInvestment,

        BigDecimal mutualFundInvestment,

        BigDecimal cryptoInvestment,

        BigDecimal goldInvestment
) {
}
