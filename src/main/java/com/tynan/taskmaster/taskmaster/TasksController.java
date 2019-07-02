package com.tynan.taskmaster.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

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


}
