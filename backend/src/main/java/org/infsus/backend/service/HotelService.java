package org.infsus.backend.service;

import java.util.*;

import org.infsus.backend.dto.*;
import org.infsus.backend.entity.Hotel;
import org.infsus.backend.entity.Room;
import org.infsus.backend.entity.User;
import org.infsus.backend.repository.HotelRepository;
import org.infsus.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
	private HotelRepository hotelRepository;
	private UserService userService;
	private RoomRepository roomRepository;
	public HotelService(HotelRepository hotelRepository, UserService userService, RoomRepository roomRepository) {
		this.hotelRepository = hotelRepository;
		this.userService = userService;
		this.roomRepository = roomRepository;
	}
	
	public HotelDTO addHotel(HotelCreateDTO hotelCreateDTO) {
		
		int numberOfHotelsOfAdmin = hotelRepository.countByAdministratorId(hotelCreateDTO.getAdministratorId());
		if (numberOfHotelsOfAdmin >= 5) {
			throw new IllegalStateException("Too much hotels for this administrator!");
		}
		
		for (int i = 0; i < hotelCreateDTO.getRooms().size(); i += 1) {
			for (int j = 0; j < hotelCreateDTO.getRooms().size(); j += 1) {
				if (i != j && hotelCreateDTO.getRooms().get(i).getNumber() == hotelCreateDTO.getRooms().get(j).getNumber()) {
					throw new IllegalStateException("Two rooms inside the same hotel can't have the same numbers!");
				}
			}
		}
		
		Hotel hotel = new Hotel();
		hotel.setName(hotelCreateDTO.getName());
		hotel.setAddress(hotelCreateDTO.getAddress());
		hotel.setDescription(hotelCreateDTO.getDescription());
		hotel.setVerified(hotelCreateDTO.isVerified());
		List<RoomCreateDTO> roomsCreateDTO = hotelCreateDTO.getRooms();
		List<Room> rooms = new ArrayList<>();
		for (RoomCreateDTO roomCreateDTO : roomsCreateDTO) {
			Room room = new Room();
			room.setNumber(roomCreateDTO.getNumber());
			room.setCapacity(roomCreateDTO.getCapacity());
			room.setPricePerNight(roomCreateDTO.getPricePerNight());
			room.setAvailable(roomCreateDTO.isAvailable());
			room.setHotel(hotel);
			rooms.add(room);
		}
		hotel.setRooms(rooms);
		User user = userService.getUserEntityById(hotelCreateDTO.getAdministratorId());
		hotel.setAdministrator(user);
		
		hotelRepository.save(hotel);
		
		HotelDTO hotelDTO = hotelToDTO(hotel);
		
		return hotelDTO;
		
	}
	
	public HotelDTO updateHotel(Long id, HotelUpdateDTO hotelUpdateDTO) {
		int numberOfHotelsOfAdmin = hotelRepository.countByAdministratorId(hotelUpdateDTO.getAdministratorId());
		if (hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found!")).getAdministrator().getId() != hotelUpdateDTO.getAdministratorId() && numberOfHotelsOfAdmin >= 5) {
			throw new IllegalStateException("Too much hotels for this administrator!");
		}
		
		for (int i = 0; i < hotelUpdateDTO.getRooms().size(); i += 1) {
			for (int j = 0; j < hotelUpdateDTO.getRooms().size(); j += 1) {
				if (i != j && hotelUpdateDTO.getRooms().get(i).getNumber() == hotelUpdateDTO.getRooms().get(j).getNumber()) {
					throw new IllegalStateException("Two rooms inside the same hotel can't have the same numbers!");
				}
			}
		}
		
		
		
		Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found"));
		hotel.setName(hotelUpdateDTO.getName());
		hotel.setAddress(hotelUpdateDTO.getAddress());
		hotel.setDescription(hotelUpdateDTO.getDescription());
		hotel.setVerified(hotelUpdateDTO.isVerified());
		
		User user = userService.getUserEntityById(hotelUpdateDTO.getAdministratorId());
		hotel.setAdministrator(user);
		
		List<Room> updatedRooms = new ArrayList<>();
		List<RoomUpdateDTO> newRooms = hotelUpdateDTO.getRooms();
		
		for (RoomUpdateDTO newRoom : newRooms) {
			if (newRoom.getId() != null) {
				Room room = roomRepository.findById(newRoom.getId()).orElseThrow(() -> new RuntimeException("Room not found"));
				room.setNumber(newRoom.getNumber());
				room.setCapacity(newRoom.getCapacity());
				room.setPricePerNight(newRoom.getPricePerNight());
				room.setAvailable(newRoom.isAvailable());
				room.setHotel(hotel);
				updatedRooms.add(room);
			} else {
				Room room = new Room();
				room.setNumber(newRoom.getNumber());
				room.setCapacity(newRoom.getCapacity());
				room.setPricePerNight(newRoom.getPricePerNight());
				room.setAvailable(newRoom.isAvailable());
				room.setHotel(hotel);
				updatedRooms.add(room);
			}
		}
		
		List<Room> currentRooms = hotel.getRooms();
		currentRooms.clear();
		currentRooms.addAll(updatedRooms);
		
		hotelRepository.save(hotel);
		
		return hotelToDTO(hotel);
	}
	
	public HotelDTO getHotelById(Long id) {
		return hotelToDTO(hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found")));
	}
	
	public HotelDTO hotelToDTO(Hotel hotel) {
		HotelDTO hotelDTO = new HotelDTO();
		hotelDTO.setId(hotel.getId());
		hotelDTO.setName(hotel.getName());
		hotelDTO.setAddress(hotel.getAddress());
		hotelDTO.setDescription(hotel.getDescription());
		hotelDTO.setVerified(hotel.isVerified());
		
		List<RoomDTO> roomsDTO = new ArrayList<>();
		List<Room> rooms = hotel.getRooms();
		for (Room room : rooms) {
			RoomDTO roomDTO = new RoomDTO();
			roomDTO.setNumber(room.getNumber());
			roomDTO.setCapacity(room.getCapacity());
			roomDTO.setPricePerNight(room.getPricePerNight());
			roomDTO.setAvailable(room.isAvailable());
			roomDTO.setHotelId(hotel.getId());
			roomDTO.setId(room.getId());
			roomsDTO.add(roomDTO);
		}
		hotelDTO.setRooms(roomsDTO);
		
		hotelDTO.setAdministratorId(hotel.getAdministrator().getId());
		
		return hotelDTO;
	}
	
	public void deleteHotel(Long id) {
		Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found"));
		hotelRepository.delete(hotel);
	}
	
	public List<HotelDTO> getAll() {
		List<Hotel> allHotels = hotelRepository.findAll();
		List<HotelDTO> allHotelsDTO = new ArrayList<>();
		for (Hotel hotel : allHotels) {
			allHotelsDTO.add(hotelToDTO(hotel));
		}
		return allHotelsDTO;
	}
}
