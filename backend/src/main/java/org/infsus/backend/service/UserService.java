package org.infsus.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.infsus.backend.dto.HotelDTO;
import org.infsus.backend.dto.RoomDTO;
import org.infsus.backend.dto.UserCreateDTO;
import org.infsus.backend.dto.UserDTO;
import org.infsus.backend.dto.UserMinimalDTO;
import org.infsus.backend.entity.Hotel;
import org.infsus.backend.entity.Room;
import org.infsus.backend.entity.User;
import org.infsus.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User getUserEntityById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		return user;
	}
	
	public UserDTO addUser(UserCreateDTO userCreateDTO) {
		
		if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
			throw new IllegalStateException("Multiple users cannot have the same email!");
		}
		
		User user = new User();
		user.setFullName(userCreateDTO.getFullName());
		user.setEmail(userCreateDTO.getEmail());
		user.setPhoneNumber(userCreateDTO.getPhoneNumber());
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<Hotel>());
		
		userRepository.save(user);
		
		UserDTO userDTO = userToDTO(user);
		
		return userDTO;
	}
	
	public UserDTO getUserById(Long id) {
		User user = getUserEntityById(id);
		UserDTO userDTO = userToDTO(user);
		return userDTO;
	}
	
	public UserDTO userToDTO(User user) {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setFullName(user.getFullName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setRole(UserDTO.Role.valueOf(user.getRole().name()));
		
		List<Hotel> hotels = user.getHotels();
		List <HotelDTO> hotelsDTO = new ArrayList<>();
		for (Hotel hotel : hotels) {
			
			HotelDTO hotelDTO = new HotelDTO();
			hotelDTO.setId(hotel.getId());
			hotelDTO.setName(hotel.getName());
			hotelDTO.setAddress(hotel.getAddress());
			hotelDTO.setDescription(hotel.getDescription());
			hotelDTO.setVerified(false);
			
			List<RoomDTO> roomsDTO = new ArrayList<>();
			List<Room> rooms = hotel.getRooms();
			for (Room room : rooms) {
				RoomDTO roomDTO = new RoomDTO();
				roomDTO.setNumber(room.getNumber());
				roomDTO.setCapacity(room.getCapacity());
				roomDTO.setPricePerNight(room.getPricePerNight());
				roomDTO.setAvailable(true);
				roomDTO.setHotelId(hotel.getId());
				roomsDTO.add(roomDTO);
			}
			hotelDTO.setRooms(roomsDTO);
			
			hotelDTO.setAdministratorId(hotel.getAdministrator().getId());
			
			
			hotelsDTO.add(hotelDTO);
		}
		
		userDTO.setHotels(hotelsDTO);
		
		return userDTO;
	}
	
	public UserDTO updateUser(Long id, UserCreateDTO userCreateDTO) {

		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

		if (!user.getEmail().equals(userCreateDTO.getEmail()) && userRepository.existsByEmail(userCreateDTO.getEmail())) {
			throw new IllegalStateException("Multiple users cannot have the same email!");
		}


		
		user.setFullName(userCreateDTO.getFullName());
		user.setEmail(userCreateDTO.getEmail());
		user.setPhoneNumber(userCreateDTO.getPhoneNumber());
		user.setRole(User.Role.ADMINISTRATOR);
		
		userRepository.save(user);
		
		return userToDTO(user);
	}
	
	public void deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		userRepository.delete(user);
	}
	
	public List<UserDTO> getAll() {
		List<User> allUsers = userRepository.findAll();
		List<UserDTO> allUsersDTO = new ArrayList<>();
		for (User user : allUsers) {
			UserDTO userDTO = userToDTO(user);
			allUsersDTO.add(userDTO);
		}
		return allUsersDTO;
	}
	
	public List<UserMinimalDTO> getAllMinimal() {
		List<User> allUsers = userRepository.findAll();
		List<UserMinimalDTO> allUsersMinimalDTO = new ArrayList<>();
		for (User user : allUsers) {
			UserMinimalDTO userMinimalDTO = userToMinimalDTO(user);
			allUsersMinimalDTO.add(userMinimalDTO);
		}
		return allUsersMinimalDTO;
	}
	
	public List<UserDTO> search(Long id, String fullName, String email, String phoneNumber) {
		List<User> filteredUsers = userRepository.findByCustomQuery(id, fullName, email, phoneNumber);
		List<UserDTO> filteredUsersDTO = new ArrayList<>();
		for (User user : filteredUsers) {
			UserDTO userDTO = userToDTO(user);
			filteredUsersDTO.add(userDTO);
		}
		return filteredUsersDTO;
	}
	
	public List<UserMinimalDTO> searchMinimal(Long id, String fullName, String email, String phoneNumber) {
		List<User> filteredUsers = userRepository.findByCustomQuery(id, fullName, email, phoneNumber);
		List<UserMinimalDTO> filteredUsersMinimalDTO = new ArrayList<>();
		for (User user : filteredUsers) {
			UserMinimalDTO userMinimalDTO = userToMinimalDTO(user);
			filteredUsersMinimalDTO.add(userMinimalDTO);
		}
		return filteredUsersMinimalDTO;
	}
	
	public UserMinimalDTO userToMinimalDTO(User user) {
		UserMinimalDTO userMinimalDTO = new UserMinimalDTO();
		userMinimalDTO.setId(user.getId());
		userMinimalDTO.setFullName(user.getFullName());
		return userMinimalDTO;
	}
}
