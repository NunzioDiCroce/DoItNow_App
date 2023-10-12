package services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import entities.User;
import exceptions.BadRequestException;
import exceptions.NotFoundException;
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
	public User createUser(UserRequestPayload payload) {
		userRepository.findByEmail(payload.getEmail()).ifPresent(user -> {
			throw new BadRequestException("The email has already been used.");
		});
		User newUser = new User(payload.getName(), payload.getSurname(), payload.getEmail(), payload.getPassword());
		return userRepository.save(newUser);
	}

	// * * * * * * * * * * find user by id * * * * * * * * * *
	public User findUserById(UUID id) throws NotFoundException {
		return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	// * * * * * * * * * * find all users (with pagination) * * * * * * * * * *
	public Page<User> findAllUsers(int page, int size, String sort) {
		return userRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
	}

	// * * * * * * * * * * update user * * * * * * * * * *
	public User updateUser(UUID id, UserRequestPayload body) throws NotFoundException {
		User userUpdated = this.findUserById(id);
		userUpdated.setName(body.getName());
		userUpdated.setSurname(body.getSurname());
		userUpdated.setEmail(body.getEmail());
		return userRepository.save(userUpdated);
	}

	// * * * * * * * * * * delete user * * * * * * * * * *
	public void deleteUser(UUID id) throws NotFoundException {
		userRepository.delete(this.findUserById(id));
	}

	// * * * * * * * * * * find user by email * * * * * * * * * *
	public User findUserByEmail(String email) throws NotFoundException {
		return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
	}

	// * * * * * * * * * * find users by name (with pagination) * * * * * * * * * *
	// TODO

	// * * * * * * * * * * find users by surname (with pagination) * * * * * * * * *
	// TODO

	// * * * * * * * * * * find users by role (with pagination) * * * * * * * * * *
	// TODO
}
