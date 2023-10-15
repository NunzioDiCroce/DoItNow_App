package services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import entities.Task;
import exceptions.NotFoundException;
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
	public Task createTask(Task task) {
		Task newTask = new Task();
		newTask.setTitle(task.getTitle());
		newTask.setDescription(task.getDescription());
		newTask.setCategory(task.getCategory());
		newTask.setExpirationDate(task.getExpirationDate());
		newTask.setCompleted(false);
		newTask.setNotes(task.getNotes());

		// user
		// TODO

		// taskId
		// TODO

		return taskRepository.save(task);
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
	public Task updateTask(UUID id, TaskRequestPayload payload) throws NotFoundException {
		Task taskUpdated = new Task();
		taskUpdated.setTitle(payload.getTitle());
		taskUpdated.setDescription(payload.getDescription());
		taskUpdated.setCategory(payload.getCategory());
		taskUpdated.setExpirationDate(payload.getExpirationDate());
		taskUpdated.setCompleted(payload.getCompleted());
		taskUpdated.setNotes(payload.getNotes());

		// user
		// TODO

		// taskId
		// TODO

		return taskRepository.save(taskUpdated);
	}

	// * * * * * * * * * * delete task * * * * * * * * * *
	public void deleteTask(UUID id) throws NotFoundException {
		taskRepository.delete(this.findtaskById(id));
	}

	// * * * * * * * * * * find tasks by taskId (with pagination) * * * * * * * * *
	// * * * * * * * * * * find tasks by title (with pagination) * * * * * * * * * *
	// * * * * * * * * * * find tasks by description (with pagination) * * * * * * *
	// * * * * * * * * * * find tasks by category (with pagination) * * * * * * * *
	// * * * * * * * * * * find tasks by expiration date (with pagination) * * * * *
	// * * * * * * * * * * find tasks by completed (with pagination) * * * * * * * *
	// * * * * * * * * * * find tasks by user (with pagination) * * * * * * * * * *
	// * * * * * * * * * * search tasks (with pagination) * * * * * * * * * *

}
