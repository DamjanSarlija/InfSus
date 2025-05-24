package org.infsus.backend.repository;

import org.infsus.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import java.util.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addUserTest() {
    	List<User> startingData = userRepository.findAll();
    	int startingSize = startingData.size();
        User user = new User();
        user.setFullName("DDDD");
        user.setEmail("DEF@gmail.com");
        user.setPhoneNumber("+123456798");
        user.setRole(User.Role.ADMINISTRATOR);
        user.setHotels(new ArrayList<>());
        userRepository.save(user);

        assertThat(userRepository.findAll()).hasSize(startingSize + 1);
    }
    
    @Test
    public void existsByEmailTest() {
    	User user = new User();
        user.setFullName("DDDD");
        user.setEmail("DEF@gmail.com");
        user.setPhoneNumber("+123456798");
        user.setRole(User.Role.ADMINISTRATOR);
        user.setHotels(new ArrayList<>());
        
        userRepository.save(user);
        
        assertThat(userRepository.existsByEmail(user.getEmail())).isTrue();   
    }
    
    @Test
    public void findByCustomQueryTest() {
    	
    	List<User> resultsBeginning = userRepository.findByCustomQuery(null, "DDDD", null, null);
    	int sizeBeginning = resultsBeginning.size();
    	
    	User user = new User();
        user.setFullName("DDDD");
        user.setEmail("DEF@gmail.com");
        user.setPhoneNumber("+123456798");
        user.setRole(User.Role.ADMINISTRATOR);
        user.setHotels(new ArrayList<>());
        
        userRepository.save(user);
        
        List<User> resultsEnd = userRepository.findByCustomQuery(null, "DDDD", null, null);
        
        assertThat(resultsEnd).hasSize(sizeBeginning + 1);
    }
    
    

}

