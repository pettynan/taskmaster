package com.tynan.taskmaster.taskmaster;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@EnableScan
public interface TasksRepository extends CrudRepository<Tasks, String> {
    Optional<Tasks> findById(String id);
//    Optional<Tasks> findByTitle()
}
