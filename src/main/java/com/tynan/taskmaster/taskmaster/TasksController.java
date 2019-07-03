package com.tynan.taskmaster.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class TasksController {

    @Autowired
    TasksRepository tasksRepository;

    @GetMapping("/tasks")
    public List getAllTasks() {
        return (List)tasksRepository.findAll();
    }

    @PostMapping("/tasks")
    public Tasks createTask(String title, String description, Optional<String> assignee) {
        Tasks newTask = new Tasks(title, description, assignee);

        tasksRepository.save(newTask);
        return newTask;
    }

    @PutMapping("/tasks/{id}/state")
    public String advanceTaskStatus(@PathVariable String id) {
        Tasks selectedTask = tasksRepository.findById(id).get();
        String currentState = selectedTask.getStatus();
        
        switch (currentState) {
            case "Available":
                selectedTask.setStatus("Assigned");
                tasksRepository.save(selectedTask);
                return "Task status was set to 'Assigned'.";

            case "Assigned":
                selectedTask.setStatus("Accepted");
                tasksRepository.save(selectedTask);
                return "Task status was set to 'Accepted'.";

            case "Accepted":
                selectedTask.setStatus("Finished");
                tasksRepository.save(selectedTask);
                return "Task status was set to 'Finished'.";

            default:
                return "Task is already Finished!";
        }
    }


    @GetMapping("/users/{name}/tasks")
    public List<Tasks> getTasksByAssignee(@PathVariable String name) {
        return tasksRepository.findAllByAssignee(name);
    }

    @PutMapping("/tasks/{id}/assign/{assignee}")
    public Tasks assignTask(@PathVariable String id,
                             @PathVariable String assignee) {
        Tasks selectedTask = tasksRepository.findById(id).get();
        selectedTask.setAssignee(assignee);
        selectedTask.setStatus("Assigned");
        tasksRepository.save(selectedTask);

        return selectedTask;
    }

}
