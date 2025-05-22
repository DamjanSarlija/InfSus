package org.infsus.backend.service;

import java.util.*;

import org.infsus.backend.dto.*;
import org.infsus.backend.entity.Hotel;
import org.infsus.backend.entity.Room;
import org.infsus.backend.entity.User;
import org.infsus.backend.repository.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
	private HotelRepository hotelRepository;
	private UserService userService;
	public HotelService(HotelRepository hotelRepository, UserService userService) {
		this.hotelRepository = hotelRepository;
		this.userService = userService;
	}
	
	public HotelDTO addHotel(HotelCreateDTO hotelCreateDTO, Long administratorId) {
		Hotel hotel = new Hotel();
		hotel.setName(hotelCreateDTO.getName());
		hotel.setAddress(hotelCreateDTO.getAddress());
		hotel.setDescription(hotelCreateDTO.getDescription());
		hotel.setVerified(false);
		List<RoomCreateDTO> roomsCreateDTO = hotelCreateDTO.getRooms();
		List<Room> rooms = new ArrayList<>();
		for (RoomCreateDTO roomCreateDTO : roomsCreateDTO) {
			Room room = new Room();
			room.setNumber(roomCreateDTO.getNumber());
			room.setCapacity(roomCreateDTO.getCapacity());
			room.setPricePerNight(roomCreateDTO.getPricePerNight());
			room.setAvailable(true);
			room.setHotel(hotel);
			rooms.add(room);
		}
		hotel.setRooms(rooms);
		User user = userService.getUserEntityById(Long.valueOf(1L));
		hotel.setAdministrator(user);
		
		hotelRepository.save(hotel);
		
		HotelDTO hotelDTO = hotelToDTO(hotel);
		
		return hotelDTO;
		
	}
	
	public HotelDTO hotelToDTO(Hotel hotel) {
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
		
		hotelDTO.setAdministrator(hotel.getAdministrator());
		
		return hotelDTO;
	}
	
	
}
