package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.User;
import exceptions.BadRequestException;
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
	public User createUser(UserRequestPayload body) {
		userRepository.findUserByEmail(body.getEmail()).ifPresent(user -> {
			throw new BadRequestException("The email has already been used.");
		});
		return null;

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
