package com.tynan.taskmaster.taskmaster.Repository;

import com.tynan.taskmaster.taskmaster.Model.Task;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface TaskRepository extends CrudRepository<Task, String> {
    Optional<Task> findById(String id);
//    Optional<Task> findByTitle()
    List<Task> findAllByAssignee(String assignee);
}
