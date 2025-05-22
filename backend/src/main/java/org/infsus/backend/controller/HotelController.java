package org.infsus.backend.controller;

import java.util.*;

import org.infsus.backend.dto.HotelCreateDTO;
import org.infsus.backend.dto.HotelDTO;
import org.infsus.backend.dto.HotelUpdateDTO;
import org.infsus.backend.dto.RoomCreateDTO;
import org.infsus.backend.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@PostMapping
	public HotelDTO addHotel(@RequestBody HotelCreateDTO hotelCreateDTO) {
		HotelDTO hotelDTO = hotelService.addHotel(hotelCreateDTO);
		return hotelDTO;
	}
	
	@PutMapping("/{id}")
	public HotelDTO updateHotel(@RequestBody HotelUpdateDTO hotelUpdateDTO, @PathVariable Long id) {
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
	
}
