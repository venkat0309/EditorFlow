package com.editorflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.editorflow.dto.common.ApiResponse;
import com.editorflow.dto.request.LoginRequest;
import com.editorflow.dto.request.RegisterRequest;
import com.editorflow.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

        @Autowired
        private AuthService authService;

        @PostMapping("/register")
        public ResponseEntity<ApiResponse<String>> register(
                        @Valid @RequestBody RegisterRequest request) {

                String message = authService.register(request);

                ApiResponse<String> response = new ApiResponse<>(
                                true,
                                "Registration Successful",
                                message);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(response);
        }

        @PostMapping("/login")
        public ResponseEntity<ApiResponse<String>> loginUser(
                        @Valid @RequestBody LoginRequest request) {

                System.out.println("Inside Login Controller");
                String token = authService.login(request);

                ApiResponse<String> response = new ApiResponse<>(
                                true,
                                "Login Successful",
                                token);

                return ResponseEntity.ok(response);
        }
}
