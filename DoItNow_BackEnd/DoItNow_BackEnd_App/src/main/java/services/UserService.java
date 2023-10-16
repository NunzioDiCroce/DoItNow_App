package services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import entities.User;
import enums.Role;
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
		User updatedUser = this.findUserById(id);
		updatedUser.setName(body.getName());
		updatedUser.setSurname(body.getSurname());
		updatedUser.setEmail(body.getEmail());
		return userRepository.save(updatedUser);
	}

	// * * * * * * * * * * delete user * * * * * * * * * *
	public void deleteUser(UUID id) throws NotFoundException {
		userRepository.delete(this.findUserById(id));
	}

	// * * * * * * * * * * find user by email * * * * * * * * * *
	// method used in AuthController
	public User findUserByEmail(String email) throws NotFoundException {
		return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
	}

	// * * * * * * * * * * find users by name (with pagination) * * * * * * * * * *
	// method not used in UserController
	public Page<User> findUsersByName(String name, int page, int size, String sort) {
		return userRepository.findByName(name, PageRequest.of(page, size, Sort.by(sort)));
	}

	// * * * * * * * * * * find users by surname (with pagination) * * * * * * * * *
	// method not used in UserController
	public Page<User> findUsersBySurname(String surname, int page, int size, String sort) {
		return userRepository.findBySurname(surname, PageRequest.of(page, size, Sort.by(sort)));
	}

	// * * * * * * * * * * find users by email (with pagination) * * * * * * * * *
	// method not used in UserController
	public Page<User> findUsersByEmail(String email, int page, int size, String sort) {
		return userRepository.findBySurname(email, PageRequest.of(page, size, Sort.by(sort)));
	}

	// * * * * * * * * * * find users by role (with pagination) * * * * * * * * * *
	// method not used in UserController
	public Page<User> findUsersByRole(Role role, int page, int size, String sort) {
		return userRepository.findByRole(role, PageRequest.of(page, size, Sort.by(sort)));
	}

	// * * * * find users by name, surname, email, role (with pagination) * * * * *
	public Page<User> searchUsers(String name, String surname, String email, Role role, int page, int size,
			String sort) {

		// probe (sonda) object definition
		User probe = new User();
		probe.setName(name);
		probe.setSurname(surname);
		probe.setEmail(email);
		probe.setRole(role);

		// example object definition
		Example<User> example = Example.of(probe);

		// pageRequest object definition
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort));

		return userRepository.findAll(example, pageRequest);
	}
}
