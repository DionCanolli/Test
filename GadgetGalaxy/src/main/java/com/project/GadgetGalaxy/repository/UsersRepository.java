package com.project.GadgetGalaxy.repository;

import com.project.GadgetGalaxy.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUserEmail(String email);
}
