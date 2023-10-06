package entities;

import java.time.LocalDate;
import java.util.UUID;

import enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {

	@Id
	@GeneratedValue
	private UUID id;

	@Column(nullable = false, unique = true)
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
	@JoinColumn(name = "user_id")
	private User user;

}
