package com.sip.ams.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sip.ams.entities.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
