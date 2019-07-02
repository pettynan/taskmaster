package com.tynan.taskmaster.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TasksController {

    @Autowired
    TasksRepository tasksRepository;

    @GetMapping("/tasks")
    public ResponseEntity<String> getTasks() {


        return new ResponseEntity<>(tasksRepository.findAll().toString(), HttpStatus.OK) ;

    }




}
