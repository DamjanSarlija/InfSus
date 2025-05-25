package org.infsus.backend.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.infsus.backend.dto.UserCreateDTO;
import org.infsus.backend.dto.UserDTO;
import org.infsus.backend.dto.UserMinimalDTO;
import org.infsus.backend.service.HotelService;
import org.infsus.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserControllerTest {
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private UserController userController;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void addUserTest() {
		
		UserCreateDTO userCreateDTO = new UserCreateDTO();
		userCreateDTO.setEmail("TestEmail@gmail.com");
		userCreateDTO.setFullName("FULLname");
		userCreateDTO.setPhoneNumber("+12345667");
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(5L);
		userDTO.setFullName("FULLname");
		userDTO.setPhoneNumber("+12345667");
		userDTO.setRole(UserDTO.Role.ADMINISTRATOR);
		userDTO.setEmail("TestEmail@gmail.com");
		userDTO.setHotels(new ArrayList<>());
		
		when(userService.addUser(userCreateDTO)).thenReturn(userDTO);
		
		UserDTO result = userController.addUser(userCreateDTO);
		
		assertThat(result).usingRecursiveComparison().isEqualTo(userDTO);
		
	}
	
	@Test
	public void getUserTest() {
		
		Long id = 5L;
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(5L);
		userDTO.setFullName("FULLname");
		userDTO.setPhoneNumber("+12345667");
		userDTO.setRole(UserDTO.Role.ADMINISTRATOR);
		userDTO.setEmail("TestEmail@gmail.com");
		
		when(userService.getUserById(id)).thenReturn(userDTO);
		
		UserDTO result = userController.getUser(id);
		
		assertThat(result).usingRecursiveComparison().isEqualTo(userDTO);
	}
	
	@Test
	public void updateUserTest() {
		
		Long id = 5L;
		
		UserCreateDTO userCreateDTO = new UserCreateDTO();
		userCreateDTO.setFullName("FULLname");
		userCreateDTO.setEmail("TestEmail@gmail.com");
		userCreateDTO.setPhoneNumber("+12345667");
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(5L);
		userDTO.setFullName("FULLname");
		userDTO.setPhoneNumber("+12345667");
		userDTO.setRole(UserDTO.Role.ADMINISTRATOR);
		userDTO.setEmail("TestEmail@gmail.com");
		
		when(userService.updateUser(id, userCreateDTO)).thenReturn(userDTO);
		
		UserDTO result = userController.updateUser(userCreateDTO, id);
		
		assertThat(result).usingRecursiveComparison().isEqualTo(userDTO);
	}
	
	@Test
	public void deleteUserTest() {
		
		Long id = 5L;
		
		ResponseEntity<String> response = userController.deleteUser(id);
		
		verify(userService).deleteUser(id);
		
		assertThat(response.getBody()).isEqualTo("User deleted successfully");
	
	}
	
	@Test
	public void getAllTest() {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(5L);
		userDTO.setFullName("FULLname");
		userDTO.setPhoneNumber("+12345667");
		userDTO.setRole(UserDTO.Role.ADMINISTRATOR);
		userDTO.setEmail("TestEmail@gmail.com");
		List<UserDTO> allUsersDTO = new ArrayList<>();
		allUsersDTO.add(userDTO);
		
		when(userService.getAll()).thenReturn(allUsersDTO);
		
		List<UserDTO> result = userController.getAll();
		
		assertThat(result).usingRecursiveComparison().isEqualTo(allUsersDTO);
	}
	
	@Test
	public void getAllMinimalTest() {
		UserMinimalDTO userMinimalDTO = new UserMinimalDTO();
		userMinimalDTO.setId(5L);
		userMinimalDTO.setFullName("FULLname");
		
		List<UserMinimalDTO> allUsersMinimalDTO = new ArrayList<>();
		allUsersMinimalDTO.add(userMinimalDTO);
		
		when(userService.getAllMinimal()).thenReturn(allUsersMinimalDTO);
		
		List<UserMinimalDTO> result = userController.getAllMinimal();
		
		assertThat(result).usingRecursiveComparison().isEqualTo(allUsersMinimalDTO);
	}
	
	@Test
	public void searchTest() {
		
		Long id = 5L;
		String fullName = "Fullname";
		String email = "Email@mail";
		String phoneNumber = "+12345";
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(5L);
		userDTO.setFullName("Fullname");
		userDTO.setPhoneNumber("+12345");
		userDTO.setRole(UserDTO.Role.ADMINISTRATOR);
		userDTO.setEmail("Email@mail");
		userDTO.setHotels(new ArrayList<>());
		List<UserDTO> usersDTO = new ArrayList<>();
		usersDTO.add(userDTO);
		
		when(userService.search(id, fullName, email, phoneNumber)).thenReturn(usersDTO);
		
		List<UserDTO> result = userController.search(id, fullName, email, phoneNumber);
		
		assertThat(result).usingRecursiveComparison().isEqualTo(usersDTO);
		
	}
	
	@Test
	public void searchMinimalTest() {
		
		Long id = 5L;
		String fullName = "Fullname";
		String email = "Email@mail";
		String phoneNumber = "+12345";
		
		UserMinimalDTO userMinimalDTO = new UserMinimalDTO();
		userMinimalDTO.setId(5L);
		userMinimalDTO.setFullName("Fullname");
		
		List<UserMinimalDTO> usersMinimalDTO = new ArrayList<>();
		usersMinimalDTO.add(userMinimalDTO);
		
		when(userService.searchMinimal(id, fullName, email, phoneNumber)).thenReturn(usersMinimalDTO);
		
		List<UserMinimalDTO> result = userController.searchMinimal(id, fullName, email, phoneNumber);
		
		assertThat(result).usingRecursiveComparison().isEqualTo(usersMinimalDTO);
		
	}
	
}
