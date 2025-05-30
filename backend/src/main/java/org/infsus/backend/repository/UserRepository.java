package org.infsus.backend.repository;

import java.util.*;

import org.infsus.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);
	
	
	@Query(value = "SELECT * FROM users WHERE (:id IS NULL OR id = :id) AND (:email IS NULL or LOWER(email) LIKE '%' || LOWER(:email) || '%') AND (:fullName IS NULL OR LOWER(full_name) LIKE '%' || LOWER(:fullName) || '%') AND (:phoneNumber IS NULL OR LOWER(phone_number) LIKE '%' || LOWER(:phoneNumber) || '%')", nativeQuery = true)
	List<User> findByCustomQuery(@Param("id") Long id, @Param("fullName") String fullName, @Param("email") String email, @Param("phoneNumber") String phoneNumber);
}
