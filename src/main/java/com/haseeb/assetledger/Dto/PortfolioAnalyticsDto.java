package com.haseeb.assetledger.Dto;

import java.math.BigDecimal;

public record PortfolioAnalyticsDto(

        BigDecimal totalInvestment,

        Long assetCount,

        BigDecimal averageInvestment,

        String largestHolding,

        BigDecimal largestHoldingAmount,

        String smallestHolding,

        BigDecimal smallestHoldingAmount

) {
}