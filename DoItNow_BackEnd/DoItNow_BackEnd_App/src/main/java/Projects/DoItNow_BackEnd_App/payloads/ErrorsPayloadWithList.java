package Projects.DoItNow_BackEnd_App.payloads;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorsPayloadWithList {

	private String message;
	private Date timestamp;
	private List<String> errorsList;

}
