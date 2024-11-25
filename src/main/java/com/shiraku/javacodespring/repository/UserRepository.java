package com.shiraku.javacodespring.repository;

import com.shiraku.javacodespring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
