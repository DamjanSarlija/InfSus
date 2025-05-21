package org.infsus.backend.controller;

import java.util.*;

import org.infsus.backend.dto.HotelCreateDTO;
import org.infsus.backend.dto.HotelDTO;
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
		HotelDTO hotelDTO = hotelService.addHotel(hotelCreateDTO, Long.valueOf(1L));
		return hotelDTO;
		
	}
	
}
