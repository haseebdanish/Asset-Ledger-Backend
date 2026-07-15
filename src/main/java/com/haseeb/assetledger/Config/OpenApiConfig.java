package com.haseeb.assetledger.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI assetLedgerAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Asset Ledger API")
                        .description("REST API for managing personal investment assets")
                        .version("1.0"));
    }
}

//http://localhost:8080/swagger-ui/index.html#/  Interactive API documentation where you can test endpoints directly.