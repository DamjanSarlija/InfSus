package org.infsus.backend.repository;

import java.util.*;

import org.infsus.backend.entity.Hotel;
import org.infsus.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long>{
	List<Hotel> findByAdministrator(User administrator);
	List<Hotel> findByName(String name);
	List<Hotel> findByAddress(String address);
	List<Hotel> findByDescription(String description);
	List<Hotel> findByVerified(boolean isVerified);
}
