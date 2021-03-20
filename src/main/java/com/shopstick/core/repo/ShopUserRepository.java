package com.shopstick.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopstick.core.entity.ShopUser;

@Repository
public interface ShopUserRepository extends JpaRepository<ShopUser, Integer> {

    public ShopUser findByUsernameAndPassword(String username, String password);
}
