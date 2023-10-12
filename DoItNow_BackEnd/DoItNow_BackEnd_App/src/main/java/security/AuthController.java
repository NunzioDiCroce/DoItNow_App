package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import entities.User;
import exceptions.UnauthorizedException;
import payloads.LoginSuccessfullPayload;
import payloads.UserLoginPayload;
import payloads.UserRequestPayload;
import services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	PasswordEncoder bcrypt;

	@Autowired
	UserService userService;

	@Autowired
	JWTTools jwtTools;

	// * * * * * * * * * * user sign in * * * * * * * * * *
	@PostMapping("/signIn")
	@ResponseStatus(HttpStatus.CREATED)
	public User userSignIn(@RequestBody @Validated UserRequestPayload payload) {
		payload.setPassword(bcrypt.encode(payload.getPassword()));
		User userCreated = userService.createUser(payload);
		return userCreated;
	}

	// * * * * * * * * * * user login * * * * * * * * * *
	@PostMapping("login")
	public LoginSuccessfullPayload userLogin(@RequestBody UserLoginPayload payload) {
		User user = userService.findUserByEmail(payload.getEmail());
		if (bcrypt.matches(payload.getPassword(), user.getPassword())) {
			String token = jwtTools.createToken(user);
			return new LoginSuccessfullPayload(token, user);
		} else {
			throw new UnauthorizedException("Invalid credentials.");
		}
	}
}
