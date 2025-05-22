package org.infsus.backend.dto;

import java.util.*;
import org.infsus.backend.entity.*;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelCreateDTO {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String address;
	
	@NotBlank
	private String description;
	
	@NotNull
	private List<RoomCreateDTO> rooms = new ArrayList<>();
	
	private boolean verified;
	
	@NotNull
	private Long administratorId;
	
	
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
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
	public Long getAdministratorId() {
		return administratorId;
	}
	public void setAdministratorId(Long administratorId) {
		this.administratorId = administratorId;
	}
	
	
}