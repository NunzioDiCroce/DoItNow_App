package Projects.DoItNow_BackEnd_App.payloads;

import lombok.Data;

@Data
public class UserLoginPayload {

	private String email;
	private String password;

}
