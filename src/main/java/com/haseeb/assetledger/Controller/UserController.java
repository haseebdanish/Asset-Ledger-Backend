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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{userid}")
    public ResponseEntity<UserResponseDto> getUserById(
            @PathVariable Long userid
    ) {
        return ResponseEntity.ok(userService.getUserById(userid));
    }
}


