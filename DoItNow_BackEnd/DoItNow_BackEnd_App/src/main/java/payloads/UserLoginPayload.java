package payloads;

import lombok.Data;

@Data
public class UserLoginPayload {

	private String email;
	private String password;

}
