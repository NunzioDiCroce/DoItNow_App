package repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

}
