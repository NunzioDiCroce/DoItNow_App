package repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.User;
import enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	// * * * * * * * * * * find user by email * * * * * * * * * *
	Optional<User> findByEmail(String email);

	// * * * * * * * * * * find users by name (with pagination) * * * * * * * * * *
	Page<User> findByName(String name, PageRequest pageRequest);

	// * * * * * * * * * * find users by surname (with pagination) * * * * * * * * *
	Page<User> findBySurname(String surname, PageRequest pageRequest);

	// * * * * * * * * * * find users by role (with pagination) * * * * * * * * * *
	Page<User> findByRole(Role role, PageRequest pageRequest);

}
