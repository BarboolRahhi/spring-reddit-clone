package com.example.springredditclone.controller;

import com.example.springredditclone.dto.*;
import com.example.springredditclone.service.AuthService;
import com.example.springredditclone.service.RefreshTokenService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>(new ApiResponse("User Registration is successful"), HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<ApiResponse> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>(new ApiResponse("Account Activate successfully"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login (@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("logout")
    public ResponseEntity<ApiResponse> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("Refresh Token Deleted Successfully!!"));
    }
}
