package Projects.DoItNow_BackEnd_App.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import Projects.DoItNow_BackEnd_App.entities.Task;
import Projects.DoItNow_BackEnd_App.entities.User;
import Projects.DoItNow_BackEnd_App.enums.Category;
import Projects.DoItNow_BackEnd_App.exceptions.NotFoundException;
import Projects.DoItNow_BackEnd_App.payloads.TaskRequestPayload;
import Projects.DoItNow_BackEnd_App.repositories.TaskRepository;
import Projects.DoItNow_BackEnd_App.repositories.UserRepository;

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
	public Task createTask(Task task, UUID userId) {

		Task newTask = new Task();

		// get user by userId to assign task
		User taskUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));

		// get user tasks to define taskId
		List<Task> userTasks = taskRepository.findAllByUser(taskUser);
		int newTaskId = userTasks.size() + 1;
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
	public Task findTaskById(UUID id) throws NotFoundException {
		return taskRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	// * * * * * * * * * * find all tasks (with pagination) * * * * * * * * * *
	public Page<Task> findAllTasks(int page, int size, String sort) {
		return taskRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
	}

	// * * * * * * * * * * update task * * * * * * * * * *
	public Task updateTask(UUID id, TaskRequestPayload payload, UUID userId) throws NotFoundException {

		Task existingTask = findTaskById(id);

		// get user by userId to assign task
		User taskUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));

		existingTask.setTaskId(existingTask.getTaskId());
		existingTask.setTitle(payload.getTitle());
		existingTask.setDescription(payload.getDescription());
		existingTask.setCategory(payload.getCategory());
		existingTask.setExpirationDate(payload.getExpirationDate());
		existingTask.setCompleted(payload.getCompleted());
		existingTask.setNotes(payload.getNotes());
		existingTask.setUser(taskUser);

		return taskRepository.save(existingTask);
	}

	// * * * * * * * * * * delete task * * * * * * * * * *
	public void deleteTask(UUID id) throws NotFoundException {
		taskRepository.delete(this.findTaskById(id));
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
