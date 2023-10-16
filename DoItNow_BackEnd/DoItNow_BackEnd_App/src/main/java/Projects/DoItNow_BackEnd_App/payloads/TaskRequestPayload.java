package Projects.DoItNow_BackEnd_App.payloads;

import java.time.LocalDate;

import Projects.DoItNow_BackEnd_App.entities.User;
import Projects.DoItNow_BackEnd_App.enums.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequestPayload {

	private String taskId;

	@NotNull(message = "The title is required.")
	@Size(min = 2, max = 30, message = "The title must have a minimum of 2 characters, a maximum of 30.")
	private String title;

	@NotNull(message = "The description is required.")
	@Size(min = 2, max = 30, message = "The description must have a minimum of 2 characters, a maximum of 30.")
	private String description;

	@NotNull(message = "The category is required.")
	private Category category;

	@NotNull(message = "The expiration date is required.")
	private LocalDate expirationDate;

	private Boolean completed;

	@Size(min = 2, max = 30, message = "The notes must have a minimum of 2 characters, a maximum of 30.")
	private String notes;

	@NotNull(message = "The user is required.")
	private User user;

}
