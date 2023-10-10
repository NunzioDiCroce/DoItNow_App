package repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID>, JpaSpecificationExecutor<Task> {
	// extends JpaSpecificationExecutor<Task> to use
	// 'taskRepository.findAll(specification, pageable)' in service

	Page<Task> findAllByTaskIdContainingIgnoreCase(String taskId, Pageable pageable);

	Page<Task> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

	Page<Task> findAllByDescriptionContainingIgnoreCase(String description, Pageable pageable);

	Page<Task> findAllByCategoryContainingIgnoreCase(String category, Pageable pageable);

	Page<Task> findAllByExpirationDateContainingIgnoreCase(String expirationDate, Pageable pageable);

	Page<Task> findAllByCompletedContainingIgnoreCase(String completed, Pageable pageable);

}
