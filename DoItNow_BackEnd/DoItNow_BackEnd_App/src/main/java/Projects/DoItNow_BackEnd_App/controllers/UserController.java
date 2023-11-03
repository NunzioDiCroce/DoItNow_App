package Projects.DoItNow_BackEnd_App.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import Projects.DoItNow_BackEnd_App.entities.User;
import Projects.DoItNow_BackEnd_App.enums.Role;
import Projects.DoItNow_BackEnd_App.payloads.UserRequestPayload;
import Projects.DoItNow_BackEnd_App.services.UserService;

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
	public User findById(@PathVariable UUID userId) {
		return userService.findById(userId);
	}

	// * * * * * * * * * * find all users (with pagination) * * * * * * * * * *
	@GetMapping
//	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<User> findAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sort) {
		return userService.find(page, size, sort);
	}

	// * * * * * * * * * * update user * * * * * * * * * *
	@PutMapping("/{userId}")
	public User updateUser(@PathVariable UUID userId, @RequestBody UserRequestPayload body) {
		return userService.findByIdAndUpdate(userId, body);
	}

	// * * * * * * * * * * delete user * * * * * * * * * *
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		userService.findByIdAndDelete(userId);
	}

	// * * * * find users by name, surname, email, role (with pagination) * * * * *
	@GetMapping("/search")
//	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<User> searchUsers(@RequestParam(required = false) String name,
			@RequestParam(required = false) String surname, @RequestParam(required = false) String email,
			@RequestParam(required = false) Role role, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sort) {
		return userService.searchUsers(name, surname, email, role, page, size, sort);
	}

}
