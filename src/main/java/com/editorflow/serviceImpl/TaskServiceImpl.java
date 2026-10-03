package com.editorflow.serviceImpl;

import com.editorflow.entity.Project;
import com.editorflow.entity.Task;
import com.editorflow.repository.ProjectRepository;
import com.editorflow.repository.TaskRepository;
import com.editorflow.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Task addTask(Long projectId, String title) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        Task task = new Task();
        task.setProject(project);
        task.setTitle(title);
        task.setCompleted(false);

        return taskRepository.save(task);
    }

    @Override
    public List<Task> getProjectTasks(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    public Task toggleTaskCompleted(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        task.setCompleted(!task.getCompleted());
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
