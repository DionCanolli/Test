package com.project.GadgetGalaxy.repository;

import com.project.GadgetGalaxy.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByRoleName(String roleName);
}
