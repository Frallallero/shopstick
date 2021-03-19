package com.shopstick.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopstick.core.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
}
