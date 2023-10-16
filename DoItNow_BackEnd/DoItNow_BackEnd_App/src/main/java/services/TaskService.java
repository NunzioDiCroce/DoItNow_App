package services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import entities.Task;
import entities.User;
import enums.Category;
import exceptions.NotFoundException;
import payloads.TaskRequestPayload;
import repositories.TaskRepository;
import repositories.UserRepository;

@Service
public class TaskService {

	private final TaskRepository taskRepository;
	private final UserRepository userRepository;

	@Autowired
	public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
	}

	// * * * * * * * * * * create task * * * * * * * * * *
	public Task createTask(Task task, String userEmail) {

		Task newTask = new Task();

		// get user by email to assign task
		User taskUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException(userEmail));

		// get user tasks to define taskId
		List<Task> userTasks = taskRepository.findAllByUser(taskUser);
		int maxTaskId = userTasks.stream().mapToInt(taskId -> Integer.parseInt(taskId.getTaskId())).max().orElse(0);
		int newTaskId = maxTaskId + 1;
		String formattedTaskId = String.format("%04d", newTaskId);

		newTask.setTaskId(formattedTaskId);
		newTask.setTitle(task.getTitle());
		newTask.setDescription(task.getDescription());
		newTask.setCategory(task.getCategory());
		newTask.setExpirationDate(task.getExpirationDate());
		newTask.setCompleted(false);
		newTask.setNotes(task.getNotes());
		newTask.setUser(taskUser);

		return taskRepository.save(newTask);
	}

	// * * * * * * * * * * find task by id * * * * * * * * *
	public Task findtaskById(UUID id) throws NotFoundException {
		return taskRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	// * * * * * * * * * * find all tasks (with pagination) * * * * * * * * * *
	public Page<Task> findAllTasks(int page, int size, String sort) {
		return taskRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
	}

	// * * * * * * * * * * update task * * * * * * * * * *
	public Task updateTask(UUID id, TaskRequestPayload payload, String userEmail) throws NotFoundException {

		Task updatedTask = new Task();

		// get user by email to assign task
		User taskUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException(userEmail));

		// get user tasks to define taskId
		List<Task> userTasks = taskRepository.findAllByUser(taskUser);
		int maxTaskId = userTasks.stream().mapToInt(taskId -> Integer.parseInt(taskId.getTaskId())).max().orElse(0);
		int newTaskId = maxTaskId + 1;
		String formattedTaskId = String.format("%04d", newTaskId);

		updatedTask.setTaskId(formattedTaskId);
		updatedTask.setTitle(payload.getTitle());
		updatedTask.setDescription(payload.getDescription());
		updatedTask.setCategory(payload.getCategory());
		updatedTask.setExpirationDate(payload.getExpirationDate());
		updatedTask.setCompleted(payload.getCompleted());
		updatedTask.setNotes(payload.getNotes());
		updatedTask.setUser(taskUser);

		return taskRepository.save(updatedTask);
	}

	// * * * * * * * * * * delete task * * * * * * * * * *
	public void deleteTask(UUID id) throws NotFoundException {
		taskRepository.delete(this.findtaskById(id));
	}

	// * * * find tasks by taskId, title, description, category, expiration date,
	// completed, user (with pagination) * * *
	public Page<Task> searchTasks(String taskId, String title, String description, Category category,
			LocalDate expirationDate, Boolean completed, int page, int size, String sort) {

		// probe (sonda) object definition
		Task probe = new Task();
		probe.setTaskId(taskId);
		probe.setTitle(title);
		probe.setDescription(description);
		probe.setCategory(category);
		probe.setExpirationDate(expirationDate);
		probe.setCompleted(completed);

		// example object definition
		Example<Task> example = Example.of(probe);

		// pageRequest object definition
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort));

		return taskRepository.findAll(example, pageRequest);
	}

}
