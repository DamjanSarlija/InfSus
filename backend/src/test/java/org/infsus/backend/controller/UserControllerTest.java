package org.infsus.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.infsus.backend.dto.UserCreateDTO;
import org.infsus.backend.dto.UserDTO;
import org.infsus.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(UserController.class)
class UserControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private UserController userController;
	
	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Test
	public void addUserTest() {
		UserCreateDTO input = new UserCreateDTO();
		input.setFullName("Karlo Mandalinic");
		input.setEmail("karman@gmail.com");
		input.setPhoneNumber("+123456798");
		
		UserDTO output = new UserDTO();
		output.setId(12L);
		output.setFullName("Karlo Mandalinic");
		output.setPhoneNumber("+123456798");
		output.setRole(UserDTO.Role.ADMINISTRATOR);
		output.setHotels(new ArrayList<>());
		
		when(userService.addUser(input)).thenReturn(output);
		
		/*mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(12L))
            .andExpect(jsonPath("$.fullName").value("Karlo Mandalinic"))
            .andExpect(jsonPath("$.phoneNumber").value("+123456798"))
            .andExpect(jsonPath("$.role").value("ADMINISTRATOR"))
            .andExpect(jsonPath("$.hotels").isArray());
		
		verify(userService).addUser(input);  */
	}
	
	@Test
	public void getUserTest() {
		Long input = 5L;
		
		UserDTO output = new UserDTO();
		output.setId(12L);
		output.setFullName("Karlo Mandalinic");
		output.setPhoneNumber("+123456798");
		output.setRole(UserDTO.Role.ADMINISTRATOR);
		output.setHotels(new ArrayList<>());
		
		when(userService.getUserById(input)).thenReturn(output);
		
		verify(userService).getUserById(input);	
	}
	
	@Test
	public void updateUserTest() {
		
	}
}