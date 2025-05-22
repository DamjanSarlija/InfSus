package org.infsus.backend.dto;

import java.util.*;
import org.infsus.backend.entity.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelCreateDTO {
	private String name;
	private String address;
	private String description;
	private List<RoomCreateDTO> rooms = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<RoomCreateDTO> getRooms() {
		return rooms;
	}
	public void setRooms(List<RoomCreateDTO> rooms) {
		this.rooms = rooms;
	}
	
}