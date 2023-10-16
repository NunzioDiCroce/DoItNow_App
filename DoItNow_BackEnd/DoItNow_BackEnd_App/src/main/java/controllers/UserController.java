package controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import entities.User;
import enums.Role;
import payloads.UserRequestPayload;
import services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// * * * * * * * * * * create user * * * * * * * * * *
	// refers to userSignIn method in AuthController

	// * * * * * * * * * * find user by id * * * * * * * * * *
	@GetMapping("/{userId}")
	public User findUserById(@PathVariable UUID userId) {
		return userService.findUserById(userId);
	}

	// * * * * * * * * * * find all users (with pagination) * * * * * * * * * *
	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<User> findAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sort) {
		return userService.findAllUsers(page, size, sort);
	}

	// * * * * * * * * * * update user * * * * * * * * * *
	@PutMapping("/{userId}")
	public User updateUser(@PathVariable UUID userId, @RequestBody UserRequestPayload payload) {
		return userService.updateUser(userId, payload);
	}

	// * * * * * * * * * * delete user * * * * * * * * * *
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		userService.deleteUser(userId);
	}

	// * * * * find users by name, surname, email, role (with pagination) * * * * *
	@GetMapping("/search")
	public Page<User> searchUsers(@RequestParam(required = false) String name,
			@RequestParam(required = false) String surname, @RequestParam(required = false) String email,
			@RequestParam(required = false) Role role, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sort) {
		return userService.searchUsers(name, surname, email, role, page, size, sort);
	}

}
