package com.tynan.taskmaster.taskmaster.Controller;

import com.tynan.taskmaster.taskmaster.Model.Task;
import com.tynan.taskmaster.taskmaster.Repository.S3Client;
import com.tynan.taskmaster.taskmaster.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class TaskController {

    private S3Client s3Client;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping("/tasks")
    public List getAllTasks() {
        return (List) taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task createTask(String title, String description, Optional<String> assignee) {
        Task newTask = new Task(title, description, assignee);

        taskRepository.save(newTask);
        return newTask;
    }

    @PutMapping("/tasks/{id}/state")
    public Task advanceTaskStatus(@PathVariable String id) {
        Task selectedTask = taskRepository.findById(id).get();
        String currentState = selectedTask.getStatus();
        
        switch (currentState) {
            case "Available":
                selectedTask.setStatus("Assigned");
                taskRepository.save(selectedTask);
                return selectedTask;

            case "Assigned":
                selectedTask.setStatus("Accepted");
                taskRepository.save(selectedTask);
                return selectedTask;

            case "Accepted":
                selectedTask.setStatus("Finished");
                taskRepository.save(selectedTask);
                return selectedTask;

            default:
                return selectedTask;
        }
    }


    @GetMapping("/users/{name}/tasks")
    public List<Task> getTasksByAssignee(@PathVariable String name) {
        return taskRepository.findAllByAssignee(name);
    }

    @PutMapping("/tasks/{id}/assign/{assignee}")
    public Task assignTask(@PathVariable String id,
                           @PathVariable String assignee) {
        Task selectedTask = taskRepository.findById(id).get();
        selectedTask.setAssignee(assignee);
        selectedTask.setStatus("Assigned");
        taskRepository.save(selectedTask);

        return selectedTask;
    }

    @PostMapping("/tasks/{id}/images")
    public Task uploadImage(@PathVariable String id,
                            @RequestPart(value = "file") MultipartFile file) {
        String image = this.s3Client.uploadFile(file);
        Task selectedTask = taskRepository.findById(id).get();

        selectedTask.setImage(image);
        taskRepository.save(selectedTask);

        return selectedTask;
    }

}
