package com.neoflex.telegram.dao;


import com.neoflex.telegram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
