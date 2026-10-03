package com.editorflow.serviceImpl;

import com.editorflow.dto.request.TaskRequest;
import com.editorflow.dto.response.TaskResponse;
import com.editorflow.entity.Priority;
import com.editorflow.entity.Project;
import com.editorflow.entity.Role;
import com.editorflow.entity.Task;
import com.editorflow.entity.TaskStatus;
import com.editorflow.entity.User;
import com.editorflow.repository.ProjectRepository;
import com.editorflow.repository.TaskRepository;
import com.editorflow.repository.UserRepository;
import com.editorflow.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskResponse addTask(Long projectId, TaskRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        Task task = new Task();
        task.setProject(project);
        applyRequest(task, request);

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public List<TaskResponse> getProjectTasks(Long projectId) {
        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TaskResponse> getTasksAssignedTo(Long editorId) {
        return taskRepository.findByAssignedToIdOrderByDueDateAsc(editorId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TaskResponse updateTask(Long taskId, TaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        applyRequest(task, request);
        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public TaskResponse toggleTaskCompleted(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        task.setCompleted(!task.getCompleted());
        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    private void applyRequest(Task task, TaskRequest request) {
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO);
        task.setPriority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM);
        task.setDueDate(request.getDueDate());

        if (request.getAssignedToId() != null) {
            task.setAssignedTo(getEditor(request.getAssignedToId()));
        }
    }

    private User getEditor(Long editorId) {
        User user = userRepository.findById(editorId)
                .orElseThrow(() -> new RuntimeException("Editor not found with id: " + editorId));

        if (user.getRole() != Role.EDITOR) {
            throw new RuntimeException("User with id " + editorId + " is not an editor");
        }

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new RuntimeException("Editor with id " + editorId + " is disabled");
        }

        return user;
    }

    private TaskResponse mapToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setProjectId(task.getProject().getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setDueDate(task.getDueDate());
        response.setCompleted(task.getCompleted());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

        if (task.getAssignedTo() != null) {
            response.setAssignedToId(task.getAssignedTo().getId());
            response.setAssignedToName(task.getAssignedTo().getName());
        }

        return response;
    }
}
