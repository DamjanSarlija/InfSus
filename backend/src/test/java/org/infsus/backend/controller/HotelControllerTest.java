package org.infsus.backend.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.infsus.backend.dto.HotelCreateDTO;
import org.infsus.backend.dto.HotelDTO;
import org.infsus.backend.dto.HotelMinimalDTO;
import org.infsus.backend.dto.HotelUpdateDTO;
import org.infsus.backend.dto.RoomCreateDTO;
import org.infsus.backend.dto.RoomDTO;
import org.infsus.backend.dto.RoomUpdateDTO;
import org.infsus.backend.dto.UserCreateDTO;
import org.infsus.backend.dto.UserDTO;
import org.infsus.backend.dto.UserMinimalDTO;
import org.infsus.backend.entity.Hotel;
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

public class HotelControllerTest {
	
	@Mock
	private HotelService hotelService;
	
	@InjectMocks
	private HotelController hotelController;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void addHotelTest() {
		
		HotelCreateDTO hotelCreateDTO = new HotelCreateDTO();
		hotelCreateDTO.setAddress("Test address");
		hotelCreateDTO.setAdministratorId(7L);
		hotelCreateDTO.setDescription("ABC");
		hotelCreateDTO.setName("Name for testing");
		hotelCreateDTO.setVerified(false);
		
		RoomCreateDTO roomCreateDTO = new RoomCreateDTO();
		roomCreateDTO.setAvailable(true);
		roomCreateDTO.setCapacity(6);
		roomCreateDTO.setNumber(202);
		roomCreateDTO.setPricePerNight(280);
		
		List<RoomCreateDTO> roomsCreateDTO = new ArrayList<>();
		roomsCreateDTO.add(roomCreateDTO);
		
		hotelCreateDTO.setRooms(roomsCreateDTO);
		
		HotelDTO hotelDTO = new HotelDTO();
		hotelDTO.setAddress("Test address");
		hotelDTO.setAdministratorId(7L);
		hotelDTO.setDescription("ABC");
		hotelDTO.setId(5L);
		hotelDTO.setName("Name for testing");
		hotelDTO.setVerified(false);
		
		RoomDTO roomDTO = new RoomDTO();
		roomDTO.setAvailable(true);
		roomDTO.setCapacity(6);
		roomDTO.setHotelId(5L);
		roomDTO.setId(8L);
		roomDTO.setNumber(202);
		roomDTO.setPricePerNight(280);
		
		List<RoomDTO> roomsDTO = new ArrayList<>();
		roomsDTO.add(roomDTO);
		
		hotelDTO.setRooms(roomsDTO);
		
		when(hotelService.addHotel(hotelCreateDTO)).thenReturn(hotelDTO);
		
		HotelDTO result = hotelController.addHotel(hotelCreateDTO);
		
		assertThat(result).usingRecursiveComparison().isEqualTo(hotelDTO);
		
	}
	
	@Test
	public void updateHotelTest() {
		
		Long id = 5L;
		
		HotelUpdateDTO hotelUpdateDTO = new HotelUpdateDTO();
		hotelUpdateDTO.setAddress("Test address");
		hotelUpdateDTO.setAdministratorId(7L);
		hotelUpdateDTO.setDescription("ABC");
		hotelUpdateDTO.setName("Name for testing");
		hotelUpdateDTO.setVerified(false);
		
		RoomUpdateDTO roomUpdateDTO = new RoomUpdateDTO();
		roomUpdateDTO.setAvailable(true);
		roomUpdateDTO.setCapacity(6);
		roomUpdateDTO.setNumber(202);
		roomUpdateDTO.setPricePerNight(280);
		
		List<RoomUpdateDTO> roomsUpdateDTO = new ArrayList<>();
		roomsUpdateDTO.add(roomUpdateDTO);
		
		hotelUpdateDTO.setRooms(roomsUpdateDTO);
		
		HotelDTO hotelDTO = new HotelDTO();
		hotelDTO.setAddress("Test address");
		hotelDTO.setAdministratorId(7L);
		hotelDTO.setDescription("ABC");
		hotelDTO.setId(5L);
		hotelDTO.setName("Name for testing");
		hotelDTO.setVerified(false);
		
		RoomDTO roomDTO = new RoomDTO();
		roomDTO.setAvailable(true);
		roomDTO.setCapacity(6);
		roomDTO.setHotelId(5L);
		roomDTO.setId(8L);
		roomDTO.setNumber(202);
		roomDTO.setPricePerNight(280);
		
		List<RoomDTO> roomsDTO = new ArrayList<>();
		roomsDTO.add(roomDTO);
		
		hotelDTO.setRooms(roomsDTO);
		
		when(hotelService.updateHotel(id, hotelUpdateDTO)).thenReturn(hotelDTO);
		
		HotelDTO result = hotelController.updateHotel(hotelUpdateDTO, id);
		
		assertThat(result).usingRecursiveComparison().isEqualTo(hotelDTO);
		
	}
	
	@Test
	public void getHotelTest() {
		
		Long id = 5L;
		
		HotelDTO hotelDTO = new HotelDTO();
		hotelDTO.setAddress("Test address");
		hotelDTO.setAdministratorId(7L);
		hotelDTO.setDescription("ABC");
		hotelDTO.setId(5L);
		hotelDTO.setName("Name for testing");
		hotelDTO.setVerified(false);
		
		RoomDTO roomDTO = new RoomDTO();
		roomDTO.setAvailable(true);
		roomDTO.setCapacity(6);
		roomDTO.setHotelId(5L);
		roomDTO.setId(8L);
		roomDTO.setNumber(202);
		roomDTO.setPricePerNight(280);
		
		List<RoomDTO> roomsDTO = new ArrayList<>();
		roomsDTO.add(roomDTO);
		
		hotelDTO.setRooms(roomsDTO);
		
		when(hotelService.getHotelById(id)).thenReturn(hotelDTO);
		
		HotelDTO result = hotelController.getHotel(id);
		
		assertThat(result).usingRecursiveComparison().isEqualTo(hotelDTO);
	}
	
	@Test
	public void deleteHotelTest() {
		
		Long id = 7L;
		
		ResponseEntity<String> response = hotelController.deleteHotel(id);
		
		verify(hotelService).deleteHotel(id);
		
		assertThat(response.getBody()).isEqualTo("Hotel deleted successfully!");
	}
	
	@Test
	public void getAllTest() {
		
		HotelDTO hotelDTO = new HotelDTO();
		hotelDTO.setAddress("Test address");
		hotelDTO.setAdministratorId(7L);
		hotelDTO.setDescription("ABC");
		hotelDTO.setId(5L);
		hotelDTO.setName("Name for testing");
		hotelDTO.setVerified(false);
		
		RoomDTO roomDTO = new RoomDTO();
		roomDTO.setAvailable(true);
		roomDTO.setCapacity(6);
		roomDTO.setHotelId(5L);
		roomDTO.setId(8L);
		roomDTO.setNumber(202);
		roomDTO.setPricePerNight(280);
		
		List<RoomDTO> roomsDTO = new ArrayList<>();
		roomsDTO.add(roomDTO);
		
		hotelDTO.setRooms(roomsDTO);
		
		List<HotelDTO> allHotelsDTO = new ArrayList<>();
		allHotelsDTO.add(hotelDTO);
		
		when(hotelService.getAll()).thenReturn(allHotelsDTO);
		
		List<HotelDTO> results = hotelController.getAll();
		
		assertThat(results).usingRecursiveComparison().isEqualTo(allHotelsDTO);
		
	}
	
	@Test
	public void getAllMinimalTest() {
		HotelMinimalDTO hotelMinimalDTO = new HotelMinimalDTO();
		hotelMinimalDTO.setAddress("Test address");
		hotelMinimalDTO.setId(5L);
		hotelMinimalDTO.setName("Name for testing");
		
		List<HotelMinimalDTO> allHotelsMinimalDTO = new ArrayList<>();
		allHotelsMinimalDTO.add(hotelMinimalDTO);
		
		when(hotelService.getAllMinimal()).thenReturn(allHotelsMinimalDTO);
		
		List<HotelMinimalDTO> results = hotelController.getAllMinimal();
		
		assertThat(results).usingRecursiveComparison().isEqualTo(allHotelsMinimalDTO);
	}
	
	@Test
	public void searchTest() {
		
		Long id = 5L;
		String address = "Test address";
		Long administratorId = 7L;
		String description = "ABC";
		String name = "Name for testing";
		boolean verified = false;
		
		
		HotelDTO hotelDTO = new HotelDTO();
		hotelDTO.setAddress("Test address");
		hotelDTO.setAdministratorId(7L);
		hotelDTO.setDescription("ABC");
		hotelDTO.setId(5L);
		hotelDTO.setName("Name for testing");
		hotelDTO.setVerified(false);
		
		RoomDTO roomDTO = new RoomDTO();
		roomDTO.setAvailable(true);
		roomDTO.setCapacity(6);
		roomDTO.setHotelId(5L);
		roomDTO.setId(8L);
		roomDTO.setNumber(202);
		roomDTO.setPricePerNight(280);
		
		List<RoomDTO> roomsDTO = new ArrayList<>();
		roomsDTO.add(roomDTO);
		
		hotelDTO.setRooms(roomsDTO);
		
		List<HotelDTO> hotelsDTO = new ArrayList<>();
		hotelsDTO.add(hotelDTO);
		
		when(hotelService.search(id, name, address, description, verified, administratorId)).thenReturn(hotelsDTO);
		
		List<HotelDTO> result = hotelController.search(id, name, address, description, verified, administratorId);
		
		assertThat(result).usingRecursiveComparison().isEqualTo(hotelsDTO);
	}
	
	@Test
	public void searchMinimalTest() {
		
		Long id = 5L;
		String address = "Test address";
		Long administratorId = 7L;
		String description = "ABC";
		String name = "Name for testing";
		boolean verified = false;
		
		
		HotelMinimalDTO hotelMinimalDTO = new HotelMinimalDTO();
		hotelMinimalDTO.setAddress("Test address");
		
		hotelMinimalDTO.setId(5L);
		hotelMinimalDTO.setName("Name for testing");
		
		
		List<HotelMinimalDTO> hotelsMinimalDTO = new ArrayList<>();
		hotelsMinimalDTO.add(hotelMinimalDTO);
		
		when(hotelService.searchMinimal(id, name, address, description, verified, administratorId)).thenReturn(hotelsMinimalDTO);
		
		List<HotelMinimalDTO> result = hotelController.searchMinimal(id, name, address, description, verified, administratorId);
		
		assertThat(result).usingRecursiveComparison().isEqualTo(hotelsMinimalDTO);
	}

}
