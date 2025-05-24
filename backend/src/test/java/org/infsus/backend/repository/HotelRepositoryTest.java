package org.infsus.backend.repository;

import org.infsus.backend.entity.Hotel;
import org.infsus.backend.entity.Room;
import org.infsus.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import java.util.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class HotelRepositoryTest {
	
	@Autowired
    private HotelRepository hotelRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void addHotelTest() {
		int currentSize = hotelRepository.findAll().size();
		
		Hotel hotel = new Hotel();
		Room room = new Room();
		
		User user = new User();
		user.setFullName("DDDD");
        user.setEmail("DEF@gmail.com");
        user.setPhoneNumber("+123456798");
        user.setRole(User.Role.ADMINISTRATOR);
        user.setHotels(new ArrayList<>());
        userRepository.save(user);
        
        Long userId = user.getId();
		
		hotel.setName("Hotel tester");
		hotel.setAddress("Testing address 123");
		hotel.setDescription("abcdefgh");
		hotel.setVerified(true);
		hotel.setAdministrator(userRepository.findById(userId).orElseThrow(() -> new RuntimeException()));
		
		room.setHotel(hotel);
		room.setAvailable(true);
		room.setCapacity(5);
		room.setNumber(202);
		room.setPricePerNight(280);
		
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		
		hotel.setRooms(rooms);
		
		hotelRepository.save(hotel);
		
		List<Hotel> results = hotelRepository.findAll();
		
		assertThat(results).hasSize(currentSize + 1);
	}
	
	@Test
	public void countByAdministratorIdTest() {
		Hotel hotel = new Hotel();
		Room room = new Room();
		
		User user = new User();
		user.setFullName("DDDD");
        user.setEmail("DEF@gmail.com");
        user.setPhoneNumber("+123456798");
        user.setRole(User.Role.ADMINISTRATOR);
        user.setHotels(new ArrayList<>());
        userRepository.save(user);
        
        Long userId = user.getId();
        
        hotel.setName("Hotel tester");
		hotel.setAddress("Testing address 123");
		hotel.setDescription("abcdefgh");
		hotel.setVerified(true);
		hotel.setAdministrator(userRepository.findById(userId).orElseThrow(() -> new RuntimeException()));
		
		room.setHotel(hotel);
		room.setAvailable(true);
		room.setCapacity(5);
		room.setNumber(202);
		room.setPricePerNight(280);
		
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		
		hotel.setRooms(rooms);
        
        Hotel hotel2 = new Hotel();
        Room room2 = new Room();
        
        hotel2.setName("Hotel tester 2");
		hotel2.setAddress("Testing address 123 2");
		hotel2.setDescription("abcdefgh 2");
		hotel2.setVerified(true);
		hotel2.setAdministrator(userRepository.findById(userId).orElseThrow(() -> new RuntimeException()));
		
		room2.setHotel(hotel2);
		room2.setAvailable(true);
		room2.setCapacity(5);
		room2.setNumber(202);
		room2.setPricePerNight(280);
		
		List<Room> rooms2 = new ArrayList<>();
		rooms2.add(room2);
		
		hotel2.setRooms(rooms2);
		
		hotelRepository.save(hotel);
		hotelRepository.save(hotel2);
		
		assertThat(hotelRepository.countByAdministratorId(userId)).isEqualTo(2);
	}
	
	@Test
	public void findByCustomQueryTest() {
		
		List<Hotel> resultsBeginning = hotelRepository.findByCustomQuery(null, "HotelCustom", "Custo", null, null, null);
    	int sizeBeginning = resultsBeginning.size();
    	
    	Hotel hotel = new Hotel();
		Room room = new Room();
		
		User user = new User();
		user.setFullName("DDDD");
        user.setEmail("DEF@gmail.com");
        user.setPhoneNumber("+123456798");
        user.setRole(User.Role.ADMINISTRATOR);
        user.setHotels(new ArrayList<>());
        userRepository.save(user);
        
        Long userId = user.getId();
		
		hotel.setName("HotelCustom");
		hotel.setAddress("Custom address 123");
		hotel.setDescription("abcdefgh");
		hotel.setVerified(true);
		hotel.setAdministrator(userRepository.findById(userId).orElseThrow(() -> new RuntimeException()));
		
		room.setHotel(hotel);
		room.setAvailable(true);
		room.setCapacity(5);
		room.setNumber(202);
		room.setPricePerNight(280);
		
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		
		hotel.setRooms(rooms);
		
		hotelRepository.save(hotel);
		
		int sizeEnd = hotelRepository.findByCustomQuery(null, "HotelCustom", "Custo", null, null, null).size();
    	
    	assertThat(sizeEnd).isEqualTo(sizeBeginning + 1);
	}

}
