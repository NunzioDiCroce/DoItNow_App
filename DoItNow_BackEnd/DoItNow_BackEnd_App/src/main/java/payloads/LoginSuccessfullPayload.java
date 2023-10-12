package payloads;

import entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSuccessfullPayload {

	private String token;
	private User user;

}
