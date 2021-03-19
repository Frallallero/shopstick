package com.shopstick.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopstick.core.entity.ShopUser;

@Repository
public interface ShopUserRepository extends JpaRepository<ShopUser, Integer> {

    public List<ShopUser> getUserByName(@Param("name") String name);
    
}
