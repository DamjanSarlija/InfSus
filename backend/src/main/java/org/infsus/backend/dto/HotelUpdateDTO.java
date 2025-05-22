package org.infsus.backend.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelUpdateDTO {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String address;
	
	@NotBlank
	private String description;
	
	@NotNull
	private List<RoomUpdateDTO> rooms;
	
	private boolean verified;
	
	@NotNull
	private Long administratorId;
	
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
	public List<RoomUpdateDTO> getRooms() {
		return rooms;
	}
	public void setRooms(List<RoomUpdateDTO> rooms) {
		this.rooms = rooms;
	}
	
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public Long getAdministratorId() {
		return administratorId;
	}
	public void setAdministratorId(Long administratorId) {
		this.administratorId = administratorId;
	}
	
	
	
	
}
