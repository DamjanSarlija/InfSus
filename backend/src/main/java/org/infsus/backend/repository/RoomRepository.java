package org.infsus.backend.repository;

import java.util.*;

import org.infsus.backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
	List<Room> findByHotelId(Long hotelId);
}
