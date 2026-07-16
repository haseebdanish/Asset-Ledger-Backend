package com.haseeb.assetledger.Dto;

import java.math.BigDecimal;

public record PortfolioAllocationDto(
        String assetType,

        BigDecimal investment,

        double percentage
) {
}
