package org.infsus.backend.controller;

import org.infsus.backend.dto.HotelCreateDTO;
import org.infsus.backend.dto.HotelDTO;
import org.infsus.backend.dto.UserCreateDTO;
import org.infsus.backend.dto.UserDTO;
import org.infsus.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public UserDTO addUser(@RequestBody UserCreateDTO userCreateDTO) {
		UserDTO userDTO = userService.addUser(userCreateDTO);
		return userDTO;
		
	}
	
	@GetMapping("/{id}")
	public UserDTO getUser(@PathVariable Long id) {
		UserDTO userDTO = userService.getUserById(id);
		return userDTO;
	}
	
	@PutMapping("/{id}")
	public UserDTO updateUser(@RequestBody UserCreateDTO userCreateDTO, @PathVariable Long id) {
		return userService.updateUser(id, userCreateDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok("User deleted successfully");
	}
}
