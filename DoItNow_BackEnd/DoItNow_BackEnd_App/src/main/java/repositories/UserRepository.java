package repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	Boolean existsByEmail(String email);

	Page<User> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<User> findAllBySurnameContainingIgnoreCase(String surname, Pageable pageable);

	Optional<User> findUserByEmail(String email);

}
