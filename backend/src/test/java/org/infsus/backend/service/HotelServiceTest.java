package org.infsus.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.infsus.backend.dto.HotelCreateDTO;
import org.infsus.backend.dto.HotelDTO;
import org.infsus.backend.entity.Hotel;
import org.infsus.backend.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class HotelServiceTest {
	
	@Mock
	private HotelRepository hotelRepository;
	
	private HotelService hotelService;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		
	}
	
	

}
