package com.editorflow.service;

import org.springframework.stereotype.Service;

import com.editorflow.dto.request.LoginRequest;
import com.editorflow.dto.request.RegisterRequest;

@Service
public interface AuthService {

    String register(RegisterRequest request);

    String login(LoginRequest request);

}
