package entities;

import java.util.UUID;

import enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue
	private UUID id;

	private String name;
	private String surname;
	private String userName;
	private String mail;
	private String password;
	private Role role;

}
