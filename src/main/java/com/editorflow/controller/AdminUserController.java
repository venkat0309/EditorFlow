package com.editorflow.controller;

import com.editorflow.dto.common.ApiResponse;
import com.editorflow.dto.request.CreateEditorRequest;
import com.editorflow.dto.response.UserResponse;
import com.editorflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/editors")
    public ResponseEntity<ApiResponse<UserResponse>> createEditor(
            @Valid @RequestBody CreateEditorRequest request) {

        UserResponse editor = userService.createEditor(request);
        ApiResponse<UserResponse> response = new ApiResponse<>(
                true,
                "Editor created successfully",
                editor);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/editors")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getEditors() {
        ApiResponse<List<UserResponse>> response = new ApiResponse<>(
                true,
                "Editors retrieved successfully",
                userService.getEditors());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/enabled")
    public ResponseEntity<ApiResponse<UserResponse>> setUserEnabled(
            @PathVariable Long userId,
            @RequestBody Map<String, Boolean> payload) {

        UserResponse user = userService.setUserEnabled(userId, payload.get("enabled"));
        ApiResponse<UserResponse> response = new ApiResponse<>(
                true,
                "User status updated successfully",
                user);

        return ResponseEntity.ok(response);
    }
}
