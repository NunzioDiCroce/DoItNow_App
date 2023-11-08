package Projects.DoItNow_BackEnd_App.controllers;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import Projects.DoItNow_BackEnd_App.entities.Task;
import Projects.DoItNow_BackEnd_App.entities.User;
import Projects.DoItNow_BackEnd_App.enums.Category;
import Projects.DoItNow_BackEnd_App.payloads.TaskRequestPayload;
import Projects.DoItNow_BackEnd_App.services.TaskService;

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
//	@PreAuthorize("hasAuthority('USER')")
	public Task createTask(@RequestBody Task task, @RequestParam UUID userId) {
		return taskService.createTask(task, userId);
	}

	// * * * * * * * * * * find task by id * * * * * * * * *
	@GetMapping("/{id}")
//	@PreAuthorize("isAuthenticated()")
	public Task findTaskById(@PathVariable UUID id) {
		return taskService.findTaskById(id);
	}

	// * * * * * * * * * * find all tasks (with pagination) * * * * * * * * * *
	@GetMapping()
//	@PreAuthorize("isAuthenticated()")
	public Page<Task> findAllTasks(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "taskId") String sort) {
		return taskService.findAllTasks(page, size, sort);
	}

	// * * * * * * * * * * find all user tasks (with pagination) * * * * * * * * * *
	@GetMapping("/usertasks")
//	@PreAuthorize("isAuthenticated()")
	public Page<Task> findAllUserTasks(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "0") int size, @RequestParam(defaultValue = "taskId") String sort) {
		UUID userId = user.getId();
		return taskService.findAllUserTasks(userId, page, size, sort);
	}

	// * * * * * * * * * * update task * * * * * * * * * *
	@PutMapping("/{id}")
//	@PreAuthorize("isAuthenticated()")
	public Task updateTask(@PathVariable UUID id, @RequestBody TaskRequestPayload payload, @RequestParam UUID userId) {
		return taskService.updateTask(id, payload, userId);
	}

	// * * * * * * * * * * delete task * * * * * * * * * *
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
//	@PreAuthorize("isAuthenticated()")
	public void deleteTask(@PathVariable UUID id) {
		taskService.deleteTask(id);
	}

	// * * * find tasks by taskId, title, description, category, expiration date,
	// completed, user (with pagination) * * *
	@GetMapping("/search")
//	@PreAuthorize("isAuthenticated()")
	public Page<Task> searchTasks(@RequestParam(required = false) String taskId,
			@RequestParam(required = false) String title, @RequestParam(required = false) String description,
			@RequestParam(required = false) Category category, @RequestParam(required = false) LocalDate expirationDate,
			@RequestParam(required = false) Boolean completed, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sort) {
		return taskService.searchTasks(taskId, title, description, category, expirationDate, completed, page, size,
				sort);
	}

}
