package com.editorflow.service;

import com.editorflow.dto.request.TaskRequest;
import com.editorflow.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse addTask(Long projectId, TaskRequest request);
    List<TaskResponse> getProjectTasks(Long projectId);
    List<TaskResponse> getTasksAssignedTo(Long editorId);
    TaskResponse updateTask(Long taskId, TaskRequest request);
    TaskResponse toggleTaskCompleted(Long taskId);
    void deleteTask(Long taskId);
}
