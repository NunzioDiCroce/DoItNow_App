package Projects.DoItNow_BackEnd_App.payloads;

import Projects.DoItNow_BackEnd_App.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginSuccessfullPayload {

	private String accessToken;
	private User user;

}
