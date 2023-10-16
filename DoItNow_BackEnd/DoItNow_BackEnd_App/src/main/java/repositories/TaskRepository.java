package repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.Task;
import entities.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

	Optional<Task> findByUser(User user);

}
