package org.infsus.backend.controller;

import java.util.*;

import org.infsus.backend.dto.HotelCreateDTO;
import org.infsus.backend.dto.HotelDTO;
import org.infsus.backend.dto.HotelMinimalDTO;
import org.infsus.backend.dto.HotelUpdateDTO;
import org.infsus.backend.dto.RoomCreateDTO;
import org.infsus.backend.dto.UserDTO;
import org.infsus.backend.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@PostMapping
	public HotelDTO addHotel(@RequestBody @Valid HotelCreateDTO hotelCreateDTO) {
		HotelDTO hotelDTO = hotelService.addHotel(hotelCreateDTO);
		return hotelDTO;
	}
	
	@PutMapping("/{id}")
	public HotelDTO updateHotel(@RequestBody @Valid HotelUpdateDTO hotelUpdateDTO, @PathVariable Long id) {
		return hotelService.updateHotel(id, hotelUpdateDTO);
	}
	
	@GetMapping("/{id}")
	public HotelDTO getHotel(@PathVariable Long id) {
		return hotelService.getHotelById(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteHotel(@PathVariable Long id) {
		hotelService.deleteHotel(id);
		return ResponseEntity.ok("Hotel deleted successfully!");
	}
	
	@GetMapping("/getAll")
	public List<HotelDTO> getAll() {
		return hotelService.getAll();
	}
	
	@GetMapping("/getAll/minimal")
	public List<HotelMinimalDTO> getAllMinimal() {
		return hotelService.getAllMinimal();
	}
	
	@GetMapping("/search")
	public List<HotelDTO> search(@RequestParam(required = false) Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String address, @RequestParam(required = false) String description, @RequestParam(required = false) Boolean verified, @RequestParam(required = false) Long administratorId) {
		return hotelService.search(id, name, address, description, verified, administratorId);
	}
	
	@GetMapping("/search/minimal")
	public List<HotelMinimalDTO> searchMinimal(@RequestParam(required = false) Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String address, @RequestParam(required = false) String description, @RequestParam(required = false) Boolean verified, @RequestParam(required = false) Long administratorId) {
		return hotelService.searchMinimal(id, name, address, description, verified, administratorId);
	}
	
}
