package com.haseeb.assetledger.Controller;


import com.haseeb.assetledger.Model.User;
import com.haseeb.assetledger.Service.UserService;
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
    public ResponseEntity<User> createUser(
            @RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/{userid}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long userid
    ) {
        return ResponseEntity.ok(userService.getUserById(userid));
    }
}


