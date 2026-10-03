package com.editorflow.service;

import com.editorflow.entity.Task;

import java.util.List;

public interface TaskService {
    Task addTask(Long projectId, String title);
    List<Task> getProjectTasks(Long projectId);
    Task toggleTaskCompleted(Long taskId);
    void deleteTask(Long taskId);
}
