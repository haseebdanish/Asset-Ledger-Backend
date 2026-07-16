package com.haseeb.assetledger.Controller;

import com.haseeb.assetledger.Dto.LoginRequestDto;
import com.haseeb.assetledger.Dto.LoginResponseDto;
import com.haseeb.assetledger.Dto.UserRequestDto;
import com.haseeb.assetledger.Dto.UserResponseDto;
import com.haseeb.assetledger.Service.AuthenticationService;
import com.haseeb.assetledger.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto request){

        return ResponseEntity.ok(authenticationService.login(request));

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @Valid @RequestBody UserRequestDto request) {

        return ResponseEntity.ok(
                userService.createUser(request)
        );
    }

}