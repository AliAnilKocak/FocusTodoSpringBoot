package com.cale.focustodo.controller;

import com.cale.focustodo.dto.LoginResponseDto;
import com.cale.focustodo.dto.RegisterResponseDto;
import com.cale.focustodo.entity.ApplicationUser;
import com.cale.focustodo.security.JwtUtilService;
import com.cale.focustodo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtilService jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @CrossOrigin("*")
    public ResponseEntity<LoginResponseDto> login(@RequestBody ApplicationUser userCredentials) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid Credentials");
        }
        return jwtUtil.generateToken(userCredentials.getUsername(),userCredentials);
    }

    @PostMapping("/register")
    @CrossOrigin("*")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody ApplicationUser user) {
        if (userService.registerUser(user)) {
            return ResponseEntity.ok(new RegisterResponseDto("User created.",true));
        }
        return ResponseEntity.ok(new RegisterResponseDto("User created faile.",false));
    }
}
