package com.shopstickcore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopstickcore.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User getUserByName(@Param("name") String name);
    
}
