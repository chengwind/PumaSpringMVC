package org.xiaoyao.demo.components.task.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.xiaoyao.demo.components.task.model.Task;

public interface TaskDao extends MongoRepository<Task, String> {

	// Page<Task> findByUserId(Long id, Pageable pageRequest);
}