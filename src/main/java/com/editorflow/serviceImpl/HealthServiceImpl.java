package com.editorflow.serviceImpl;

import com.editorflow.dto.HealthResponse;
import com.editorflow.service.HealthService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HealthServiceImpl implements HealthService {

    @Override
    public HealthResponse getHealth() {

        return new HealthResponse(
                "UP",
                "EditorFlow",
                "1.0.0",
                LocalDateTime.now());
    }
}