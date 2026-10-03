package com.editorflow.service;

import com.editorflow.dto.request.CreateEditorRequest;
import com.editorflow.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createEditor(CreateEditorRequest request);
    List<UserResponse> getEditors();
    UserResponse setUserEnabled(Long userId, Boolean enabled);
}
