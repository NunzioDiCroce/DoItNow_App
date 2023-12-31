package Projects.DoItNow_BackEnd_App.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(UUID id) {
		super("Item with id: " + id + " not found.");
	}

}
