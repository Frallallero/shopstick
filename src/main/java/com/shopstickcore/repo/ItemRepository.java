package com.shopstickcore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopstickcore.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

}
