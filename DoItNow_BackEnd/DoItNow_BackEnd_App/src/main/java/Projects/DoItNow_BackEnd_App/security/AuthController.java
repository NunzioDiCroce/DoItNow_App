package Projects.DoItNow_BackEnd_App.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import Projects.DoItNow_BackEnd_App.entities.User;
import Projects.DoItNow_BackEnd_App.exceptions.UnauthorizedException;
import Projects.DoItNow_BackEnd_App.payloads.LoginSuccessfullPayload;
import Projects.DoItNow_BackEnd_App.payloads.UserLoginPayload;
import Projects.DoItNow_BackEnd_App.payloads.UserRequestPayload;
import Projects.DoItNow_BackEnd_App.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	JWTTools jwtTools;

	@Autowired
	PasswordEncoder bcrypt;

	// * * * * * * * * * * user register * * * * * * * * * *
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody @Validated UserRequestPayload body) {
		body.setPassword(bcrypt.encode(body.getPassword()));
		User created = userService.create(body);
		return created;
	}

	// * * * * * * * * * * user login * * * * * * * * * *
	@PostMapping("/login")
	public LoginSuccessfullPayload login(@RequestBody UserLoginPayload body) {
		User user = userService.findByEmail(body.getEmail());
		if (bcrypt.matches(body.getPassword(), user.getPassword())) {
			String token = jwtTools.createToken(user);
			return new LoginSuccessfullPayload(token, user);
		} else {
			throw new UnauthorizedException("Invalid credentials.");
		}
	}

}
