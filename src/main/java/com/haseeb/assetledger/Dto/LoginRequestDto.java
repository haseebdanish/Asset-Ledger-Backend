package com.haseeb.assetledger.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequestDto {

        private String email;
        private String password;

}
