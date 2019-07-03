package com.tynan.taskmaster.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

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
    public String advanceTaskStatus(@PathVariable String id) {
        Task selectedTask = taskRepository.findById(id).get();
        String currentState = selectedTask.getStatus();
        
        switch (currentState) {
            case "Available":
                selectedTask.setStatus("Assigned");
                taskRepository.save(selectedTask);
                return "Task status was set to 'Assigned'.";

            case "Assigned":
                selectedTask.setStatus("Accepted");
                taskRepository.save(selectedTask);
                return "Task status was set to 'Accepted'.";

            case "Accepted":
                selectedTask.setStatus("Finished");
                taskRepository.save(selectedTask);
                return "Task status was set to 'Finished'.";

            default:
                return "Task is already Finished!";
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

}
