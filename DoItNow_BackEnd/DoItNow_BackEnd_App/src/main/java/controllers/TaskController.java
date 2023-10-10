package controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import entities.Task;
import services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private final TaskService taskService;

	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	// * * * * * * * * * * create task * * * * * * * * * *
	@PostMapping
	public ResponseEntity<String> createTask(@RequestBody Task task) {
		try {
			taskService.createTask(task);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Task created successfully with id: " + task.getTaskId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating the task.");
		}
	}

	// * * * * * * * * * * find tasks by taskId (with pagination) * * * * * * * * *
	@GetMapping()
	public ResponseEntity<?> findTasksByTaskId(@RequestParam String taskId, @RequestParam int page,
			@RequestParam int size, @RequestParam String sort) {
		try {
			return taskService.findTasksByTaskId(taskId, page, size, sort);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error finding tasks by task id: " + taskId);
		}
	}

	// * * * * * * * * * * find tasks by title (with pagination) * * * * * * * * * *
	@GetMapping()
	public ResponseEntity<?> findTasksByTitle(@RequestParam String title, @RequestParam int page,
			@RequestParam int size, @RequestParam String sort) {
		try {
			return taskService.findTasksByTitle(title, page, size, sort);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error finding tasks by title: " + title);
		}
	}

	// * * * * * * * * * * find tasks by description (with pagination) * * * * * * *
	@GetMapping()
	public ResponseEntity<?> findTasksByDescription(@RequestParam String description, @RequestParam int page,
			@RequestParam int size, @RequestParam String sort) {
		try {
			return taskService.findTasksByDescription(description, page, size, sort);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error finding tasks by description: " + description);
		}
	}

	// * * * * * * * * * * find tasks by category (with pagination) * * * * * * * *
	@GetMapping()
	public ResponseEntity<?> findTasksByCategory(@RequestParam String category, @RequestParam int page,
			@RequestParam int size, @RequestParam String sort) {
		try {
			return taskService.findTasksByCategory(category, page, size, sort);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error finding tasks by category: " + category);
		}
	}

	// * * * * * * * * * * find tasks by expiration date (with pagination) * * * * *
	@GetMapping()
	public ResponseEntity<?> findTasksByExpirationDate(@RequestParam String expirationDate, @RequestParam int page,
			@RequestParam int size, @RequestParam String sort) {
		try {
			return taskService.findTasksByExpirationDate(expirationDate, page, size, sort);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error finding tasks by expiration date: " + expirationDate);
		}
	}

	// * * * * * * * * * * find tasks by completed (with pagination) * * * * * * * *
	@GetMapping()
	public ResponseEntity<?> findTasksByCompleted(@RequestParam String completed, @RequestParam int page,
			@RequestParam int size, @RequestParam String sort) {
		try {
			return taskService.findTasksByCompleted(completed, page, size, sort);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error finding tasks by completed: " + completed);
		}
	}

	// * * * * * * * * * * find tasks by user (with pagination) * * * * * * * * * *
	// TODO

	// * * * * * * * * * * search tasks (with pagination) * * * * * * * * * *
	@GetMapping("/search")
	public ResponseEntity<?> searchTasks(@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "expirationDate", required = false) String expirationDate,
			@RequestParam(value = "completed", required = false) String completed,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sort) {
		try {
			return taskService.searchTask(title, description, category, expirationDate, completed, page, size, sort);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error searching tasks.");
		}
	}

	// * * * * * * * * * * find all tasks (with pagination) * * * * * * * * * *
	@GetMapping()
	public ResponseEntity<?> findAllTasks(@RequestParam int page, @RequestParam int size, @RequestParam String sort) {
		try {
			return taskService.findAllTasks(page, size, sort);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error finding tasks.");
		}
	}
	// * * * * * * * * * * update task * * * * * * * * * *

	// * * * * * * * * * * delete task * * * * * * * * * *
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable UUID id) {
		try {
			taskService.deleteTask(id);
			return ResponseEntity.ok("Task deleted successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Task not found.");
		}
	}

}
