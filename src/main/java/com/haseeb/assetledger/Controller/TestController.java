//package com.haseeb.assetledger.Controller;
//
//import com.haseeb.assetledger.Dto.LoginRequestDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequestMapping("/test")
//@RequiredArgsConstructor
//public class TestController {
//
//    private final AuthenticationManager authenticationManager;
//
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequestDto request) {
//
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.email(),
//                        request.password()
//                )
//        );
//
//        return "Login Successful!";
//    }
//}
