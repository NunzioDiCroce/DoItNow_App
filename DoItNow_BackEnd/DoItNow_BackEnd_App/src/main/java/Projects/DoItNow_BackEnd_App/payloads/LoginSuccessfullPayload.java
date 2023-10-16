package Projects.DoItNow_BackEnd_App.payloads;

import Projects.DoItNow_BackEnd_App.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSuccessfullPayload {

	private String token;
	private User user;

}
