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

	// * * * * * * * * * * find user by email * * * * * * * * * *
	// TODO

	// * * * * * * * * * * find users by name (with pagination) * * * * * * * * * *
	// TODO

	// * * * * * * * * * * find users by surname (with pagination) * * * * * * * * *
	// TODO

	// * * * * * * * * * * find users by role (with pagination) * * * * * * * * * *
	// TODO

}
