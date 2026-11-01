package com.haseeb.assetledger.Service;

import com.haseeb.assetledger.Dto.LoginResponseDto;
import com.haseeb.assetledger.Dto.LoginRequestDto;
import com.haseeb.assetledger.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponseDto login(LoginRequestDto request) {

        String normalizedEmail = request.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        normalizedEmail,
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(normalizedEmail);

        return new LoginResponseDto(token);
    }

}