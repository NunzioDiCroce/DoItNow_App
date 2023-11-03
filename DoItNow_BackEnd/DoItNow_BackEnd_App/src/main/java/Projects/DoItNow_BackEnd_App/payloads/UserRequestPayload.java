package Projects.DoItNow_BackEnd_App.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequestPayload {

	@NotNull(message = "The name is required.")
	@Size(min = 2, max = 30, message = "The name must have a minimum of 2 characters, a maximum of 30.")
	private String name;

	@NotNull(message = "The surname is required.")
	@Size(min = 2, max = 30, message = "The surname must have a minimum of 2 characters, a maximum of 30.")
	private String surname;

	@NotNull(message = "The email is required.")
	@Email(message = "The email is not a valid address.")
	private String email;

	@NotNull(message = "The password is required.")
	@Size(min = 4, message = "The password must have a minimum of 4 characters.")
	private String password;

}
