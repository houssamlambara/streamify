package com.houssam.user_service.repository;

import com.houssam.user_service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
