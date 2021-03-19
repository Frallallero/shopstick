package com.shopstick.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopstick.core.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

}
