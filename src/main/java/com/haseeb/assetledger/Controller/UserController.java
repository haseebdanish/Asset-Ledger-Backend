package com.haseeb.assetledger.Controller;


import com.haseeb.assetledger.Dto.UserRequestDto;
import com.haseeb.assetledger.Dto.UserResponseDto;
import com.haseeb.assetledger.Model.User;
import com.haseeb.assetledger.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(
            Authentication authentication) {

        return ResponseEntity.ok(
                userService.getCurrentUser(authentication.getName())
        );
    }
}


