package org.infsus.backend.repository;

import java.util.*;

import org.infsus.backend.entity.Hotel;
import org.infsus.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HotelRepository extends JpaRepository<Hotel, Long>{
	
	int countByAdministratorId(Long id);
	
	
	@Query(value = "SELECT * FROM hotel WHERE (:id IS NULL OR id = :id) AND (:name IS NULL or name LIKE '%' || :name || '%') AND (:address IS NULL OR address LIKE '%' || :address || '%') AND (:description IS NULL OR description LIKE '%' || :description || '%') AND (:verified IS NULL OR verified = :verified) AND (:administratorId IS NULL OR user_id = :administratorId)", nativeQuery = true)
	List<Hotel> findByCustomQuery(@Param("id") Long id, @Param("name") String name, @Param("address") String address, @Param("description") String description, @Param("verified") Boolean verified, @Param("administratorId") Long administratorId);
}
