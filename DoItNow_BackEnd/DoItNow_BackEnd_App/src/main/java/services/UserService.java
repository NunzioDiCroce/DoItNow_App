package services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entities.User;
import enums.Role;
import payloads.UserRequestPayload;
import repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// * * * * * * * * * * create user * * * * * * * * * *
	@Transactional
	public ResponseEntity<String> createUser(UserRequestPayload body) {
		if (userRepository.existsByEmail(body.getEmail())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The email has already been used.");
		}

		User newUser = new User(body.getName(), body.getSurname(), body.getEmail(), body.getPassword());
		userRepository.save(newUser);

		return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
	}

	// * * * * * * * * * * find user by id * * * * * * * * * *
	@Transactional
	public ResponseEntity<?> findUserById(UUID id) {
		Optional<User> optionalUser = userRepository.findById(id);

		if (optionalUser.isPresent()) {
			return ResponseEntity.ok(optionalUser.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
	}

	// * * * * * * * * * * find users by name (with pagination) * * * * * * * * * *
	@Transactional
	public ResponseEntity<?> findUsersByName(String name, int page, int size, String sort) {
		Page<User> users = userRepository.findAllByNameContainingIgnoreCase(name,
				PageRequest.of(page, size, Sort.by(name)));

		if (users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found for name: " + name);
		} else {
			return ResponseEntity.ok(users);
		}
	}

	// * * * * * * * * * * find users by surname (with pagination) * * * * * * * * *
	@Transactional
	public ResponseEntity<?> findUsersBySurname(String surname, int page, int size, String sort) {
		Page<User> users = userRepository.findAllBySurnameContainingIgnoreCase(surname,
				PageRequest.of(page, size, Sort.by(surname)));

		if (users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found for surname: " + surname);
		} else {
			return ResponseEntity.ok(users);
		}
	}

	// * * * * * * * * * * find user by email * * * * * * * * * *
	@Transactional
	public ResponseEntity<?> findUserByEmail(String email) {
		Optional<User> optionalUser = userRepository.findUserByEmail(email);

		if (optionalUser.isPresent()) {
			return ResponseEntity.ok(optionalUser.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for email: " + email);
		}
	}

	// * * * * * * * * * * find users by role (with pagination) * * * * * * * * * *
	@Transactional
	public ResponseEntity<?> findUsersByRole(Role role, int page, int size, String sort) {
		Page<User> users = userRepository.findUserByRole(role, PageRequest.of(page, size, Sort.by(sort)));

		if (users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found by role: " + role);
		} else {
			return ResponseEntity.ok(users);
		}
	}

	// * * * * * * * * * * find all users (with pagination) * * * * * * * * * *
	@Transactional
	public ResponseEntity<?> findAllUsers(int page, int size, String sort) {
		Page<User> users = userRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));

		if (users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found.");
		} else {
			return ResponseEntity.ok(users);
		}
	}

	// * * * * * * * * * * update user * * * * * * * * * *
	@Transactional
	public ResponseEntity<String> updateUser(UUID id, UserRequestPayload body) {
		Optional<User> optionalUser = userRepository.findById(id);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setName(body.getName());
			user.setSurname(body.getSurname());
			user.setEmail(body.getEmail());
			user.setPassword(body.getPassword());
			userRepository.save(user);
			return ResponseEntity.ok("User updated successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
	}

	// * * * * * * * * * * delete user * * * * * * * * * *
	@Transactional
	public ResponseEntity<String> deleteUser(UUID id) {
		Optional<User> optionalUser = userRepository.findById(id);

		if (optionalUser.isPresent()) {
			userRepository.delete(optionalUser.get());
			return ResponseEntity.ok("User deleted successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
	}

}
