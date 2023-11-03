package Projects.DoItNow_BackEnd_App.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import Projects.DoItNow_BackEnd_App.entities.User;
import Projects.DoItNow_BackEnd_App.enums.Role;
import Projects.DoItNow_BackEnd_App.exceptions.BadRequestException;
import Projects.DoItNow_BackEnd_App.exceptions.NotFoundException;
import Projects.DoItNow_BackEnd_App.payloads.UserRequestPayload;
import Projects.DoItNow_BackEnd_App.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// * * * * * * * * * * create user * * * * * * * * * *
	public User create(UserRequestPayload body) {
		userRepository.findByEmail(body.getEmail()).ifPresent(user -> {
			throw new BadRequestException("The email has already been used.");
		});
		User newUser = new User(body.getName(), body.getSurname(), body.getEmail(), body.getPassword());
		return userRepository.save(newUser);
	}

	// * * * * * * * * * * find user by id * * * * * * * * * *
	public User findById(UUID id) throws NotFoundException {
		return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	// * * * * * * * * * * find all users (with pagination) * * * * * * * * * *
	public Page<User> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return userRepository.findAll(pageable);
	}

	// * * * * * * * * * * update user * * * * * * * * * *
	public User findByIdAndUpdate(UUID id, UserRequestPayload body) throws NotFoundException {
		User found = this.findById(id);
		found.setEmail(body.getEmail());
		found.setName(body.getName());
		found.setSurname(body.getSurname());
		return userRepository.save(found);
	}

	// * * * * * * * * * * delete user * * * * * * * * * *
	public void findByIdAndDelete(UUID id) throws NotFoundException {
		User found = this.findById(id);
		userRepository.delete(found);
	}

	// * * * * * * * * * * find user by email * * * * * * * * * *
	// method used in AuthController
	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User with email " + email + " not found."));
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
