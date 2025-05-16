package com.citizen.engagement_system_be.repository;


import com.citizen.engagement_system_be.enums.UserRole;
import com.citizen.engagement_system_be.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);

    Optional<User> findByRole(UserRole role);
}
