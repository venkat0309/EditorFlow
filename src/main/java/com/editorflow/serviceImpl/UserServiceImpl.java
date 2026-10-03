package com.editorflow.serviceImpl;

import com.editorflow.dto.request.CreateEditorRequest;
import com.editorflow.dto.response.UserResponse;
import com.editorflow.entity.Role;
import com.editorflow.entity.User;
import com.editorflow.exception.EmailAlreadyExistsException;
import com.editorflow.repository.UserRepository;
import com.editorflow.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createEditor(CreateEditorRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.EDITOR);
        user.setEnabled(true);

        return mapToResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getEditors() {
        return userRepository.findByRoleOrderByCreatedAtDesc(Role.EDITOR)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse setUserEnabled(Long userId, Boolean enabled) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setEnabled(Boolean.TRUE.equals(enabled));
        return mapToResponse(userRepository.save(user));
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setEnabled(user.getEnabled());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
