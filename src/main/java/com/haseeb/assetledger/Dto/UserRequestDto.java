package com.haseeb.assetledger.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(
        @NotBlank
        String userName,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
}
