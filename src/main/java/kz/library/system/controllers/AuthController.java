package kz.library.system.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.library.system.models.request.AuthenticationRequest;
import kz.library.system.models.request.RegisterRequest;
import kz.library.system.models.response.AuthenticationResponse;
import kz.library.system.security.service.AuthService;
import kz.library.system.security.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "auth", description = "Auth Management APIs")
@Slf4j
public class AuthController {

    private final AuthService service;
    private final TokenBlacklistService tokenBlacklistService;
    private static final String SWAGGER_TAG = "auth";

    @PostMapping("/register")
    @Operation(summary = "Register User",
            description = "Register user",
            tags = {SWAGGER_TAG})
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Login User",
            description = "Login user",
            tags = {SWAGGER_TAG})
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout User",
            description = "Logout user",
            tags = {SWAGGER_TAG})
    public void logout(@RequestHeader("Authorization")String token){
        String jwt = token.substring("Bearer ".length());
        tokenBlacklistService.addToBlacklist(jwt);
        log.debug("User logout");
    }
}
