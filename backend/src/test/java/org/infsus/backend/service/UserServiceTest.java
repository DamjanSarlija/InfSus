package org.infsus.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.infsus.backend.dto.UserCreateDTO;
import org.infsus.backend.dto.UserDTO;
import org.infsus.backend.dto.UserMinimalDTO;
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
	
	@Test
	public void getUserEntityByIdExistsTest() {
		
		Long id = 3L;
		
		User user = new User();
		user.setId(3L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		when(userRepository.findById(id)).thenReturn(Optional.of(user));
		
		User result = userService.getUserEntityById(id);
		
		assertThat(result.getId()).isEqualTo(id);
	}
	
	@Test
	public void getUserEntityByIdDoesNotExistTest() {
		
		Long id = 3L;
		
		User user = new User();
		user.setId(3L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		when(userRepository.findById(id)).thenThrow(new RuntimeException());
		
		assertThatThrownBy(() -> userService.getUserEntityById(id)).isInstanceOf(RuntimeException.class);
		
	}
	
	@Test
	public void userToDTOTest() {
		User user = new User();
		user.setId(3L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		UserDTO userDTO = userService.userToDTO(user);
		
		assertThat(userDTO.getId()).isEqualTo(user.getId());
		assertThat(userDTO.getFullName()).isEqualTo(user.getFullName());
		assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());
		assertThat(userDTO.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
		assertThat(userDTO.getRole().name()).isEqualTo(user.getRole().name());
		assertThat(userDTO.getHotels()).isEqualTo(user.getHotels());
	}
	
	@Test
	public void updateUserNoEmailChangeTest() {
		User user = new User();
		user.setId(3L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		UserCreateDTO userCreateDTO = new UserCreateDTO();
		userCreateDTO.setFullName("Test Name 2");
		userCreateDTO.setEmail("unique.test.email@gmail.com");
		userCreateDTO.setPhoneNumber("+385996652");
		
		User userNew = new User();
		userNew.setId(3L);
		userNew.setFullName("Test Name 2");
		userNew.setEmail("unique.test.email@gmail.com");
		userNew.setPhoneNumber("+385996652");
		userNew.setRole(User.Role.ADMINISTRATOR);
		userNew.setHotels(new ArrayList<>());
		
		when(userRepository.findById(3L)).thenReturn(Optional.of(user));
		when(userRepository.existsByEmail("unique.test.email@gmail.com")).thenReturn(true);
		when(userRepository.save(any(User.class))).thenReturn(userNew);
		
		UserDTO result = userService.updateUser(3L, userCreateDTO);
		
		assertThat(result.getId()).isEqualTo(3L);
		assertThat(result.getEmail()).isEqualTo("unique.test.email@gmail.com");
		assertThat(result.getPhoneNumber()).isEqualTo("+385996652");
		assertThat(result.getFullName()).isEqualTo("Test Name 2");
		
		
	}
	
	@Test
	public void updateUserEmailChangeFailTest() {
		User user = new User();
		user.setId(3L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		UserCreateDTO userCreateDTO = new UserCreateDTO();
		userCreateDTO.setFullName("Test Name 2");
		userCreateDTO.setEmail("unique.test.email.CHANGED@gmail.com");
		userCreateDTO.setPhoneNumber("+385996652");
		
		User userNew = new User();
		userNew.setId(3L);
		userNew.setFullName("Test Name 2");
		userNew.setEmail("unique.test.email.CHANGED@gmail.com");
		userNew.setPhoneNumber("+385996652");
		userNew.setRole(User.Role.ADMINISTRATOR);
		userNew.setHotels(new ArrayList<>());
		
		when(userRepository.findById(3L)).thenReturn(Optional.of(user));
		when(userRepository.existsByEmail("unique.test.email.CHANGED@gmail.com")).thenReturn(true);
		when(userRepository.save(any(User.class))).thenReturn(userNew);
		
		assertThatThrownBy(() -> userService.updateUser(3L, userCreateDTO)).isInstanceOf(IllegalStateException.class);	
	}
	
	@Test
	public void deleteUserTest() {
		Long id = 7L;
		
		User user = new User();
		user.setId(id);
		
		when(userRepository.findById(id)).thenReturn(Optional.of(user));
		
		userService.deleteUser(id);
		
		verify(userRepository).delete(user);
	}
	
	@Test
	public void getAllTest() {
		
		User user = new User();
		user.setId(3L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		List<User> allUsers = new ArrayList<>();
		allUsers.add(user);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(3L);
		userDTO.setFullName("Test Name");
		userDTO.setEmail("unique.test.email@gmail.com");
		userDTO.setPhoneNumber("+38599665");
		userDTO.setRole(UserDTO.Role.ADMINISTRATOR);
		userDTO.setHotels(new ArrayList<>());
		
		List<UserDTO> allUsersDTO = new ArrayList<>();
		allUsersDTO.add(userDTO);
		
		when(userRepository.findAll()).thenReturn(allUsers);
		
		List<UserDTO> results = userService.getAll();
		
		assertThat(results).usingRecursiveComparison().isEqualTo(allUsersDTO);
	}
	
	@Test
	public void getAllMinimalTest() {
		
		User user = new User();
		user.setId(3L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		List<User> allUsers = new ArrayList<>();
		allUsers.add(user);
		
		UserMinimalDTO userMinimalDTO = new UserMinimalDTO();
		userMinimalDTO.setId(3L);
		userMinimalDTO.setFullName("Test Name");
		
		List<UserMinimalDTO> allUsersMinimalDTO = new ArrayList<>();
		allUsersMinimalDTO.add(userMinimalDTO);
		
		when(userRepository.findAll()).thenReturn(allUsers);
		
		List<UserMinimalDTO> results = userService.getAllMinimal();
		
		assertThat(results).usingRecursiveComparison().isEqualTo(allUsersMinimalDTO);
	}

	@Test
	public void searchTest() {
		Long id = 4L;
		String fullName = "Name for testing";
		String email = "testtest@tst.com";
		String phoneNumber = "+34567876";
		
		User user = new User();
		user.setId(id);
		user.setFullName(fullName);
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		List<User> allUsers = new ArrayList<>();
		allUsers.add(user);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(id);
		userDTO.setFullName(fullName);
		userDTO.setEmail(email);
		userDTO.setPhoneNumber(phoneNumber);
		userDTO.setRole(UserDTO.Role.ADMINISTRATOR);
		userDTO.setHotels(new ArrayList<>());
		
		List<UserDTO> allUsersDTO = new ArrayList<>();
		allUsersDTO.add(userDTO);
		
		when(userRepository.findByCustomQuery(id, fullName, email, phoneNumber)).thenReturn(allUsers);
		
		List<UserDTO> results = userService.search(id, fullName, email, phoneNumber);
		
		assertThat(results).usingRecursiveComparison().isEqualTo(allUsersDTO);
	}
	
	@Test
	public void searchMinimalTest() {
		Long id = 4L;
		String fullName = "Name for testing";
		String email = "testtest@tst.com";
		String phoneNumber = "+34567876";
		
		User user = new User();
		user.setId(id);
		user.setFullName(fullName);
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		List<User> allUsers = new ArrayList<>();
		allUsers.add(user);
		
		UserMinimalDTO userMinimalDTO = new UserMinimalDTO();
		userMinimalDTO.setId(id);
		userMinimalDTO.setFullName(fullName);
		
		
		List<UserMinimalDTO> allUsersMinimalDTO = new ArrayList<>();
		allUsersMinimalDTO.add(userMinimalDTO);
		
		when(userRepository.findByCustomQuery(id, fullName, email, phoneNumber)).thenReturn(allUsers);
		
		List<UserMinimalDTO> results = userService.searchMinimal(id, fullName, email, phoneNumber);
		
		assertThat(results).usingRecursiveComparison().isEqualTo(allUsersMinimalDTO);
	}
	
	@Test
	public void userToMinimalDTOTest() {
		User user = new User();
		user.setId(3L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		UserMinimalDTO userMinimalDTO = userService.userToMinimalDTO(user);
		
		assertThat(userMinimalDTO.getId()).isEqualTo(user.getId());
		assertThat(userMinimalDTO.getFullName()).isEqualTo(user.getFullName());
		
	}

}
