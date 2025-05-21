package org.infsus.backend.entity;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String fullName;
	private String email;
	private String phoneNumber;
	
	public enum Role {
		GUEST,
		ADMINISTRATOR,
		SUPERVISOR
	}
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToMany(mappedBy = "administrator", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Hotel> hotels = new ArrayList<>();
}
