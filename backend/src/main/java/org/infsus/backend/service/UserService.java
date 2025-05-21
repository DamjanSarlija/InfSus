package org.infsus.backend.service;

import org.infsus.backend.entity.User;
import org.infsus.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User getUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		return user;
	}
	
	
}
