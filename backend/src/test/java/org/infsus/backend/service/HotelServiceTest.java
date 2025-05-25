package org.infsus.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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
import org.infsus.backend.dto.RoomUpdateDTO;
import org.infsus.backend.entity.Hotel;
import org.infsus.backend.entity.Room;
import org.infsus.backend.entity.User;
import org.infsus.backend.repository.HotelRepository;
import org.infsus.backend.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class HotelServiceTest {
	
	@Mock
	private HotelRepository hotelRepository;
	
	@Mock
	private UserService userService;
	
	@Mock
	private RoomRepository roomRepository;
	
	private HotelService hotelService;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		hotelService = new HotelService(hotelRepository, userService, roomRepository);
	}
	
	@Test
	public void addHotelSuccessTest() {
		
		HotelCreateDTO hotelCreateDTO = new HotelCreateDTO();
		hotelCreateDTO.setName("Testni Hotel");
		hotelCreateDTO.setAddress("Ulica Jedinicnog Testa 28");
		hotelCreateDTO.setDescription("Ovaj hotel namijenjen je testiranju");
		hotelCreateDTO.setVerified(false);
		hotelCreateDTO.setAdministratorId(5L);
		
		RoomCreateDTO roomCreateDTO = new RoomCreateDTO();
		roomCreateDTO.setNumber(202);
		roomCreateDTO.setCapacity(5);
		roomCreateDTO.setAvailable(true);
		roomCreateDTO.setPricePerNight(150);
		
		RoomCreateDTO roomCreateDTO2 = new RoomCreateDTO();
		roomCreateDTO2.setNumber(303);
		roomCreateDTO2.setCapacity(4);
		roomCreateDTO2.setAvailable(false);
		roomCreateDTO2.setPricePerNight(100);
		
		List<RoomCreateDTO> roomsCreateDTO = new ArrayList<>();
		roomsCreateDTO.add(roomCreateDTO);
		roomsCreateDTO.add(roomCreateDTO2);
		
		hotelCreateDTO.setRooms(roomsCreateDTO);
		
		User user = new User();
		user.setId(5L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		Hotel hotel = new Hotel();
		hotel.setName(hotelCreateDTO.getName());
		hotel.setAddress(hotelCreateDTO.getAddress());
		hotel.setDescription(hotelCreateDTO.getDescription());
		hotel.setVerified(hotelCreateDTO.isVerified());
		hotel.setAdministrator(user);
		
		Room room = new Room();
		room.setNumber(roomCreateDTO.getNumber());
		room.setCapacity(roomCreateDTO.getCapacity());
		room.setAvailable(roomCreateDTO.isAvailable());
		room.setPricePerNight(roomCreateDTO.getPricePerNight());
		room.setHotel(hotel);
		
		Room room2 = new Room();
		room2.setNumber(roomCreateDTO2.getNumber());
		room2.setCapacity(roomCreateDTO2.getCapacity());
		room2.setAvailable(roomCreateDTO2.isAvailable());
		room2.setPricePerNight(roomCreateDTO2.getPricePerNight());
		room2.setHotel(hotel);
		
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		rooms.add(room2);
		
		hotel.setRooms(rooms);
		
		when(hotelRepository.countByAdministratorId(hotelCreateDTO.getAdministratorId())).thenReturn(0);
		when(userService.getUserEntityById(5L)).thenReturn(user);
		when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
		
		HotelDTO result = hotelService.addHotel(hotelCreateDTO);
		
		assertThat(result.getName()).isEqualTo(hotelCreateDTO.getName());
		assertThat(result.getAddress()).isEqualTo(hotelCreateDTO.getAddress());
		assertThat(result.getDescription()).isEqualTo(hotelCreateDTO.getDescription());
		assertThat(result.getAdministratorId()).isEqualTo(hotelCreateDTO.getAdministratorId());
		assertThat(result.getRooms().size()).isEqualTo(hotelCreateDTO.getRooms().size());
	}
	
	@Test
	public void updateHotelTest() {
		
		Long id = 5L;
		
		HotelUpdateDTO hotelUpdateDTO = new HotelUpdateDTO();
		hotelUpdateDTO.setName("Testni Hotel");
		hotelUpdateDTO.setAddress("Ulica Jedinicnog Testa 28");
		hotelUpdateDTO.setDescription("Ovaj hotel namijenjen je testiranju");
		hotelUpdateDTO.setVerified(false);
		hotelUpdateDTO.setAdministratorId(5L);
		
		RoomUpdateDTO roomUpdateDTO = new RoomUpdateDTO();
		roomUpdateDTO.setNumber(202);
		roomUpdateDTO.setCapacity(5);
		roomUpdateDTO.setAvailable(true);
		roomUpdateDTO.setPricePerNight(150);
		
		RoomUpdateDTO roomUpdateDTO2 = new RoomUpdateDTO();
		roomUpdateDTO2.setNumber(303);
		roomUpdateDTO2.setCapacity(4);
		roomUpdateDTO2.setAvailable(false);
		roomUpdateDTO2.setPricePerNight(100);
		
		List<RoomUpdateDTO> roomsUpdateDTO = new ArrayList<>();
		roomsUpdateDTO.add(roomUpdateDTO);
		roomsUpdateDTO.add(roomUpdateDTO2);
		
		hotelUpdateDTO.setRooms(roomsUpdateDTO);
		
		User user = new User();
		user.setId(7L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		Hotel hotel = new Hotel();
		hotel.setId(5L);
		hotel.setName("Ime drugacije na pocetku");
		hotel.setAddress(hotelUpdateDTO.getAddress());
		hotel.setDescription(hotelUpdateDTO.getDescription());
		hotel.setVerified(hotelUpdateDTO.isVerified());
		hotel.setAdministrator(user);
		
		Room room = new Room();
		room.setNumber(roomUpdateDTO.getNumber());
		room.setCapacity(roomUpdateDTO.getCapacity());
		room.setAvailable(roomUpdateDTO.isAvailable());
		room.setPricePerNight(roomUpdateDTO.getPricePerNight());
		room.setHotel(hotel);
		
		Room room2 = new Room();
		room2.setNumber(roomUpdateDTO2.getNumber());
		room2.setCapacity(roomUpdateDTO2.getCapacity());
		room2.setAvailable(roomUpdateDTO2.isAvailable());
		room2.setPricePerNight(roomUpdateDTO2.getPricePerNight());
		room2.setHotel(hotel);
		
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		rooms.add(room2);
		
		hotel.setRooms(rooms);
		
		when(hotelRepository.countByAdministratorId(user.getId())).thenReturn(0);
		when(hotelRepository.findById(5L)).thenReturn(Optional.of(hotel));
		when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
		when(roomRepository.findById(room2.getId())).thenReturn(Optional.of(room2));
		when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
		when(userService.getUserEntityById(hotelUpdateDTO.getAdministratorId())).thenReturn(user);
		
		HotelDTO results = hotelService.updateHotel(id, hotelUpdateDTO);
		
		assertThat(results.getName()).isEqualTo(hotelUpdateDTO.getName());
	}
	
	@Test
	public void getHotelByIdTest() {
		
		Long id = 5L;
		
		User user = new User();
		user.setId(5L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		Hotel hotel = new Hotel();
		hotel.setId(5L);
		hotel.setName("Test Hotel");
		hotel.setAddress("Test address");
		hotel.setDescription("Test description");
		hotel.setVerified(false);
		hotel.setAdministrator(user);
		
		Room room = new Room();
		room.setNumber(202);
		room.setCapacity(5);
		room.setAvailable(true);
		room.setPricePerNight(140);
		room.setHotel(hotel);
		
		Room room2 = new Room();
		room2.setNumber(200);
		room2.setCapacity(7);
		room2.setAvailable(false);
		room2.setPricePerNight(300);
		room2.setHotel(hotel);
		
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		rooms.add(room2);
		
		hotel.setRooms(rooms);
		
		
		when(hotelRepository.findById(id)).thenReturn(Optional.of(hotel));
		
		HotelDTO result = hotelService.getHotelById(id);
		
		assertThat(result.getName()).isEqualTo(hotel.getName());
		assertThat(result.getId()).isEqualTo(5L);
		assertThat(result.getAddress()).isEqualTo(hotel.getAddress());
	}
	
	@Test
	public void deleteHotelTest() {
		
		Long id = 5L;
		
		User user = new User();
		user.setId(5L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		Hotel hotel = new Hotel();
		hotel.setId(5L);
		hotel.setName("Test Hotel");
		hotel.setAddress("Test address");
		hotel.setDescription("Test description");
		hotel.setVerified(false);
		hotel.setAdministrator(user);
		
		Room room = new Room();
		room.setNumber(202);
		room.setCapacity(5);
		room.setAvailable(true);
		room.setPricePerNight(140);
		room.setHotel(hotel);
		
		Room room2 = new Room();
		room2.setNumber(200);
		room2.setCapacity(7);
		room2.setAvailable(false);
		room2.setPricePerNight(300);
		room2.setHotel(hotel);
		
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		rooms.add(room2);
		
		hotel.setRooms(rooms);
		
		when(hotelRepository.findById(id)).thenReturn(Optional.of(hotel));
		
		hotelService.deleteHotel(id);
		
		verify(hotelRepository).delete(hotel);
	}
	
	@Test
	public void getAllTest() {
		
		Long id = 5L;
		
		User user = new User();
		user.setId(5L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		Hotel hotel = new Hotel();
		hotel.setId(5L);
		hotel.setName("Test Hotel");
		hotel.setAddress("Test address");
		hotel.setDescription("Test description");
		hotel.setVerified(false);
		hotel.setAdministrator(user);
		
		Room room = new Room();
		room.setNumber(202);
		room.setCapacity(5);
		room.setAvailable(true);
		room.setPricePerNight(140);
		room.setHotel(hotel);
		
		Room room2 = new Room();
		room2.setNumber(200);
		room2.setCapacity(7);
		room2.setAvailable(false);
		room2.setPricePerNight(300);
		room2.setHotel(hotel);
		
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		rooms.add(room2);
		
		hotel.setRooms(rooms);
		
		List<Hotel> allHotels = new ArrayList<>();
		allHotels.add(hotel);
		user.setHotels(allHotels);
		
		when(hotelRepository.findAll()).thenReturn(allHotels);
		
		List<HotelDTO> results = hotelService.getAll();
		
		assertThat(results.get(0).getName()).isEqualTo(allHotels.get(0).getName());
		assertThat(results.get(0).getAddress()).isEqualTo(allHotels.get(0).getAddress());
		assertThat(results.get(0).getDescription()).isEqualTo(allHotels.get(0).getDescription());
		assertThat(results.get(0).getAdministratorId()).isEqualTo(allHotels.get(0).getAdministrator().getId());
		assertThat(results.get(0).isVerified()).isEqualTo(allHotels.get(0).isVerified());
		assertThat(results.get(0).getId()).isEqualTo(allHotels.get(0).getId());
		assertThat(results.get(0).getRooms().size()).isEqualTo(allHotels.get(0).getRooms().size());
	}
	
	@Test
	public void getAllMinimalTest() {
		
		Long id = 5L;
		
		User user = new User();
		user.setId(5L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		Hotel hotel = new Hotel();
		hotel.setId(5L);
		hotel.setName("Test Hotel");
		hotel.setAddress("Test address");
		hotel.setDescription("Test description");
		hotel.setVerified(false);
		hotel.setAdministrator(user);
		
		Room room = new Room();
		room.setNumber(202);
		room.setCapacity(5);
		room.setAvailable(true);
		room.setPricePerNight(140);
		room.setHotel(hotel);
		
		Room room2 = new Room();
		room2.setNumber(200);
		room2.setCapacity(7);
		room2.setAvailable(false);
		room2.setPricePerNight(300);
		room2.setHotel(hotel);
		
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		rooms.add(room2);
		
		hotel.setRooms(rooms);
		
		List<Hotel> allHotels = new ArrayList<>();
		allHotels.add(hotel);
		user.setHotels(allHotels);
		
		when(hotelRepository.findAll()).thenReturn(allHotels);
		
		List<HotelMinimalDTO> results = hotelService.getAllMinimal();
		
		assertThat(results.get(0).getName()).isEqualTo(allHotels.get(0).getName());
		assertThat(results.get(0).getAddress()).isEqualTo(allHotels.get(0).getAddress());
	}
	
	@Test
	public void searchTest() {
		
		Long id = 5L;
		String name = "Name";
		String address = "Address";
		String description = "Description";
		boolean verified = true;
		Long administratorId = 7L;
		
		User user = new User();
		user.setId(5L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		Hotel hotel = new Hotel();
		hotel.setId(5L);
		hotel.setName("Test Hotel");
		hotel.setAddress("Test address");
		hotel.setDescription("Test description");
		hotel.setVerified(false);
		hotel.setAdministrator(user);
		
		Room room = new Room();
		room.setNumber(202);
		room.setCapacity(5);
		room.setAvailable(true);
		room.setPricePerNight(140);
		room.setHotel(hotel);
		
		Room room2 = new Room();
		room2.setNumber(200);
		room2.setCapacity(7);
		room2.setAvailable(false);
		room2.setPricePerNight(300);
		room2.setHotel(hotel);
		
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		rooms.add(room2);
		
		hotel.setRooms(rooms);
		
		List<Hotel> hotels = new ArrayList<>();
	    hotels.add(hotel);
		user.setHotels(hotels);
		
		when(hotelRepository.findByCustomQuery(id, name, address, description, verified, administratorId)).thenReturn(hotels);
		
		List<HotelDTO> results = hotelService.search(id, name, address, description, verified, administratorId);
		
		assertThat(results.get(0).getName()).isEqualTo(hotels.get(0).getName());
		assertThat(results.get(0).getAddress()).isEqualTo(hotels.get(0).getAddress());
		assertThat(results.get(0).getDescription()).isEqualTo(hotels.get(0).getDescription());
		assertThat(results.get(0).getAdministratorId()).isEqualTo(hotels.get(0).getAdministrator().getId());
		assertThat(results.get(0).isVerified()).isEqualTo(hotels.get(0).isVerified());
		assertThat(results.get(0).getId()).isEqualTo(hotels.get(0).getId());
		assertThat(results.get(0).getRooms().size()).isEqualTo(hotels.get(0).getRooms().size());
	}
	
	
	@Test
	public void searchMinimalTest() {
		
		Long id = 5L;
		String name = "Name";
		String address = "Address";
		String description = "Description";
		boolean verified = true;
		Long administratorId = 7L;
		
		User user = new User();
		user.setId(5L);
		user.setFullName("Test Name");
		user.setEmail("unique.test.email@gmail.com");
		user.setPhoneNumber("+38599665");
		user.setRole(User.Role.ADMINISTRATOR);
		user.setHotels(new ArrayList<>());
		
		Hotel hotel = new Hotel();
		hotel.setId(5L);
		hotel.setName("Test Hotel");
		hotel.setAddress("Test address");
		hotel.setDescription("Test description");
		hotel.setVerified(false);
		hotel.setAdministrator(user);
		
		Room room = new Room();
		room.setNumber(202);
		room.setCapacity(5);
		room.setAvailable(true);
		room.setPricePerNight(140);
		room.setHotel(hotel);
		
		Room room2 = new Room();
		room2.setNumber(200);
		room2.setCapacity(7);
		room2.setAvailable(false);
		room2.setPricePerNight(300);
		room2.setHotel(hotel);
		
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		rooms.add(room2);
		
		hotel.setRooms(rooms);
		
		List<Hotel> hotels = new ArrayList<>();
	    hotels.add(hotel);
		user.setHotels(hotels);
		
		when(hotelRepository.findByCustomQuery(id, name, address, description, verified, administratorId)).thenReturn(hotels);
		
		List<HotelMinimalDTO> results = hotelService.searchMinimal(id, name, address, description, verified, administratorId);
		
		assertThat(results.get(0).getName()).isEqualTo(hotels.get(0).getName());
		assertThat(results.get(0).getAddress()).isEqualTo(hotels.get(0).getAddress());
	}

}
