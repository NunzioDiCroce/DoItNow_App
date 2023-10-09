package services;

import org.springframework.beans.factory.annotation.Autowired;
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

}
