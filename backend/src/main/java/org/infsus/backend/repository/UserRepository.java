package org.infsus.backend.repository;

import org.infsus.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);
}
