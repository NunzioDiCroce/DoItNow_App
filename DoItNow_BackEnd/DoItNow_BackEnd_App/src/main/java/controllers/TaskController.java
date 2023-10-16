package controllers;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import entities.Task;
import enums.Category;
import payloads.TaskRequestPayload;
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
	@ResponseStatus(HttpStatus.CREATED)
	public Task createTask(@RequestBody Task task, @RequestParam String userEmail) {
		return taskService.createTask(task, userEmail);
	}

	// * * * * * * * * * * find task by id * * * * * * * * *
	@GetMapping("/{taskId}")
	public Task findtaskById(@PathVariable UUID id) {
		return taskService.findtaskById(id);
	}

	// * * * * * * * * * * find all tasks (with pagination) * * * * * * * * * *
	@GetMapping()
	public Page<Task> findAllTasks(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "taskId") String sort) {
		return taskService.findAllTasks(page, size, sort);
	}

	// * * * * * * * * * * update task * * * * * * * * * *
	@PutMapping("/{taskId}")
	public Task updateTask(@PathVariable UUID taskId, @RequestBody TaskRequestPayload payload,
			@RequestParam String userEmail) {
		return taskService.updateTask(taskId, payload, userEmail);
	}

	// * * * * * * * * * * delete task * * * * * * * * * *
	@DeleteMapping("/{taskId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTask(@PathVariable UUID taskId) {
		taskService.deleteTask(taskId);
	}

	// * * * find tasks by taskId, title, description, category, expiration date,
	// completed, user (with pagination) * * *
	@GetMapping("/search")
	public Page<Task> searchTasks(@RequestParam(required = false) String taskId,
			@RequestParam(required = false) String title, @RequestParam(required = false) String description,
			@RequestParam(required = false) Category category, @RequestParam(required = false) LocalDate expirationDate,
			@RequestParam(required = false) Boolean completed, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sort) {
		return taskService.searchTasks(taskId, title, description, category, expirationDate, completed, page, size,
				sort);
	}

}
