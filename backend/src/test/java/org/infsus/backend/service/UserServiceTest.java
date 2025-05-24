package org.infsus.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.infsus.backend.dto.UserCreateDTO;
import org.infsus.backend.dto.UserDTO;
import org.infsus.backend.entity.User;
import org.infsus.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	private UserService userService;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		userService = new UserService(userRepository);
	}
	
	@Test
	public void addUserUniqueEmailTest() {
		
		UserCreateDTO userCreateDTO = new UserCreateDTO();
		userCreateDTO.setFullName("Test Name");
		userCreateDTO.setEmail("unique.test.email@gmail.com");
		userCreateDTO.setPhoneNumber("+38599665");
		
		User user = new User();
		user.setId(1L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(userRepository.existsByEmail(userCreateDTO.getEmail())).thenReturn(false);
		
		UserDTO result = userService.addUser(userCreateDTO);
		
		
		
		assertThat(result.getFullName()).isEqualTo(userCreateDTO.getFullName());
		assertThat(result.getEmail()).isEqualTo(userCreateDTO.getEmail());
		assertThat(result.getPhoneNumber()).isEqualTo(userCreateDTO.getPhoneNumber());
		assertThat(result.getId()).isNotNull();
	}
	
	@Test
	public void addUserExistingEmailTest() {
		
		UserCreateDTO userCreateDTO = new UserCreateDTO();
		userCreateDTO.setFullName("Test Name");
		userCreateDTO.setEmail("unique.test.email@gmail.com");
		userCreateDTO.setPhoneNumber("+38599665");
		
		User user = new User();
		user.setId(1L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(userRepository.existsByEmail(userCreateDTO.getEmail())).thenReturn(true);
		
		assertThatThrownBy(() -> userService.addUser(userCreateDTO)).isInstanceOf(IllegalStateException.class);
	}
	
	@Test
	public void getUserByIdTest() {
		Long id = 3L;
		
		User user = new User();
		user.setId(3L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		when(userRepository.findById(id)).thenReturn(Optional.of(user));
		
		UserDTO result = userService.getUserById(id);
		
		assertThat(result.getId()).isEqualTo(3L);
	}
	
	@Test
	public void getUserByIdNonExistantTest() {
		Long id = 3L;
		
		User user = new User();
		user.setId(3L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		when(userRepository.findById(id)).thenThrow(new RuntimeException());
		
		assertThatThrownBy(() -> userService.getUserById(id)).isInstanceOf(RuntimeException.class);
	}

}
