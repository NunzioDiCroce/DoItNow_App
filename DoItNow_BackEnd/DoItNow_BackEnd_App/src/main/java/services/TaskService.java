package services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entities.Task;
import payloads.TaskRequestPayload;
import repositories.TaskRepository;

@Service
public class TaskService {

	private final TaskRepository taskRepository;

	@Autowired
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	// * * * * * * * * * * create task * * * * * * * * * *
	@Transactional
	public ResponseEntity<String> createTask(TaskRequestPayload body) {

		Task newTask = new Task();

		// other properties
		newTask.setTitle(body.getTitle());
		newTask.setDescription(body.getDescription());
		newTask.setCategory(body.getCategory());
		newTask.setExpirationDate(body.getExpirationDate());
		newTask.setCompleted(false);
		newTask.setNotes(body.getNotes());

		// user
		// TODO

		// taskId
		// TODO

		taskRepository.save(newTask);

		return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully.");
	}

	// * * * * * * * * * * find tasks by taskId (with pagination) * * * * * * * * *
	@Transactional
	public ResponseEntity<?> findTasksByTaskId(String taskId, int page, int size, String sort) {
		Page<Task> tasks = taskRepository.findAllByTaskIdContainingIgnoreCase(taskId,
				PageRequest.of(page, size, Sort.by(taskId)));

		if (tasks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tasks not found for task id: " + taskId);
		} else {
			return ResponseEntity.ok(tasks);
		}
	}

	// * * * * * * * * * * find tasks by title (with pagination) * * * * * * * * * *
	@Transactional
	public ResponseEntity<?> findTasksByTitle(String title, int page, int size, String sort) {
		Page<Task> tasks = taskRepository.findAllByTitleContainingIgnoreCase(title,
				PageRequest.of(page, size, Sort.by(title)));

		if (tasks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tasks not found for title: " + title);
		} else {
			return ResponseEntity.ok(tasks);
		}
	}

	// * * * * * * * * * * find tasks by description (with pagination) * * * * * * *
	@Transactional
	public ResponseEntity<?> findTasksByDescription(String description, int page, int size, String sort) {
		Page<Task> tasks = taskRepository.findAllByDescriptionContainingIgnoreCase(description,
				PageRequest.of(page, size, Sort.by(description)));

		if (tasks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tasks not found for description: " + description);
		} else {
			return ResponseEntity.ok(tasks);
		}
	}

	// * * * * * * * * * * find tasks by category (with pagination) * * * * * * * *
	@Transactional
	public ResponseEntity<?> findTasksByCategory(String category, int page, int size, String sort) {
		Page<Task> tasks = taskRepository.findAllByCategoryContainingIgnoreCase(category,
				PageRequest.of(page, size, Sort.by(category)));

		if (tasks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tasks not found for category: " + category);
		} else {
			return ResponseEntity.ok(tasks);
		}
	}

	// * * * * * * * * * * find tasks by expiration date (with pagination) * * * * *
	@Transactional
	public ResponseEntity<?> findTasksByExpirationDate(String expirationDate, int page, int size, String sort) {
		Page<Task> tasks = taskRepository.findAllByExpirationDateContainingIgnoreCase(expirationDate,
				PageRequest.of(page, size, Sort.by(expirationDate)));

		if (tasks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Tasks not found for expiration date: " + expirationDate);
		} else {
			return ResponseEntity.ok(tasks);
		}
	}

	// * * * * * * * * * * find tasks by completed (with pagination) * * * * * * * *
	@Transactional
	public ResponseEntity<?> findTasksByCompleted(String completed, int page, int size, String sort) {
		Page<Task> tasks = taskRepository.findAllByCompletedContainingIgnoreCase(completed,
				PageRequest.of(page, size, Sort.by(completed)));

		if (tasks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tasks not found for completed: " + completed);
		} else {
			return ResponseEntity.ok(tasks);
		}
	}

	// * * * * * * * * * * find tasks by user (with pagination) * * * * * * * * * *
	// TODO

	// * * * * * * * * * * find all tasks (with pagination) * * * * * * * * * *
	@Transactional
	public ResponseEntity<?> findAllTasks(int page, int size, String sort) {
		Page<Task> tasks = taskRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));

		if (tasks.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tasks found.");
		} else {
			return ResponseEntity.ok(tasks);
		}
	}

	// * * * * * * * * * * update task * * * * * * * * * *
	@Transactional
	public ResponseEntity<String> updateTask(UUID id, TaskRequestPayload body) {
		Optional<Task> optionalTask = taskRepository.findById(id);

		if (optionalTask.isPresent()) {
			Task task = optionalTask.get();
			task.setTitle(body.getTitle());
			task.setDescription(body.getDescription());
			task.setCategory(body.getCategory());
			task.setExpirationDate(body.getExpirationDate());
			task.setCompleted(false);
			task.setNotes(body.getNotes());

			// user
			// TODO

			// taskId
			// TODO

			taskRepository.save(task);
			return ResponseEntity.ok("Task updated successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
		}
	}

	// * * * * * * * * * * delete task * * * * * * * * * *
	@Transactional
	public ResponseEntity<String> deleteTask(UUID id) {
		Optional<Task> optionalTask = taskRepository.findById(id);

		if (optionalTask.isPresent()) {
			taskRepository.delete(optionalTask.get());
			return ResponseEntity.ok("Task deleted successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
		}
	}

}
