package Projects.DoItNow_BackEnd_App.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Projects.DoItNow_BackEnd_App.entities.Task;
import Projects.DoItNow_BackEnd_App.entities.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

	List<Task> findAllByUser(User user);

	Page<Task> findByUser(User user, Pageable pageable);

}