package com.haseeb.assetledger.Controller;


import com.haseeb.assetledger.Dto.UserRequestDto;
import com.haseeb.assetledger.Dto.UserResponseDto;
import com.haseeb.assetledger.Model.User;
import com.haseeb.assetledger.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/{userid}")
    public ResponseEntity<UserResponseDto> getUserById(
            @PathVariable Long userid
    ) {
        return ResponseEntity.ok(userService.getUserById(userid));
    }
}


