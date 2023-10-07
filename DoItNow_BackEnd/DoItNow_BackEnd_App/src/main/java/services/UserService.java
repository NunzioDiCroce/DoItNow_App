package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import entities.User;
import payloads.UserRequestPayload;
import repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// create user
//	public User createUser(UserRequestPayload body) {
//		userRepository.findUserByEmail(body.getEmail()).ifPresent(user -> {
//			throw new BadRequestException("The email has already been used.");
//		});
//		User newUser = new User(body.getName(), body.getSurname(), body.getUserName(), body.getEmail(),
//				body.getPassword());
//		return userRepository.save(newUser);
//
//	}

	public ResponseEntity<String> createUser(UserRequestPayload body) {
//		if (userRepository.existsByEmail(body.getEmail())) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The email has already been used.");
//		}
		User newUser = new User(body.getName(), body.getSurname(), body.getUserName(), body.getEmail(),
				body.getPassword());
		userRepository.save(newUser);
		userRepository.existsById(null);
		return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");

	}

	// find user by id

	// find users by name (with pagination)

	// find users by surname (with pagination)

	// find user by userName

	// find user by email

	// find users by role (with pagination)

	// find all users (with pagination)

	// update user

	// delete user

}
