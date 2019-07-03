package com.tynan.taskmaster.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RestController
public class TasksController {

    @Autowired
    TasksRepository tasksRepository;

    @GetMapping("/tasks")
    public List getTasks() {

        return (List)tasksRepository.findAll();
    }

    @PostMapping("/tasks")
    public RedirectView createTask(String title, String description) {
        Tasks newTask = new Tasks(title, description);

        tasksRepository.save(newTask);
        return new RedirectView("/tasks");
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


}
