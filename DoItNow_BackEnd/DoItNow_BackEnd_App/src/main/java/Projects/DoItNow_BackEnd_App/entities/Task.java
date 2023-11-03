package Projects.DoItNow_BackEnd_App.entities;

import java.time.LocalDate;
import java.util.UUID;

import Projects.DoItNow_BackEnd_App.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {

	@Id
	@GeneratedValue
	private UUID id;

	@Column(nullable = false)
	private String taskId;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Category category;

	@Column(nullable = false)
	private LocalDate expirationDate;

	@Column(nullable = false)
	private Boolean completed;

	private String notes;

	@ManyToOne
	private User user;

}
