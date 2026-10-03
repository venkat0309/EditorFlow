package com.editorflow.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.editorflow.dto.request.LoginRequest;
import com.editorflow.dto.request.RegisterRequest;
import com.editorflow.entity.Role;
import com.editorflow.entity.User;
import com.editorflow.exception.EmailAlreadyExistsException;
import com.editorflow.repository.UserRepository;
import com.editorflow.security.CustomUserDetails;
import com.editorflow.security.CustomUserDetailsService;
import com.editorflow.security.JwtService;
import com.editorflow.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole(Role.EDITOR);

        userRepository.save(user);
        return "Hello " + request.getName() + " your account created sucessfully...!";
    }

    @Override
    public String login(LoginRequest request) {

        System.out.println("Step 1");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        System.out.println("Step 2");

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService
                .loadUserByUsername(request.getEmail());

        System.out.println("Step 3");

        return jwtService.generateToken(userDetails);
    }
}
