package com.shopstickcore.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopstickcore.entity.Item;
import com.shopstickcore.entity.Role;
import com.shopstickcore.repo.ItemRepository;
import com.shopstickcore.repo.RoleRepository;

@RestController
@RequestMapping("/core-rest")
public class CoreController {

	private static final Logger logger = LoggerFactory.getLogger(CoreController.class);

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ItemRepository itemRepository;

	
//	ROLES
	@GetMapping("/roles")
	public List<Role> retrieveRoles() {
		logger.info("Retrieve roles");
		return roleRepository.findAll();
	}
	
//	USERS
	
//	ITEMS
	@GetMapping("/items")
	public List<Item> retrieveItems() {
		logger.info("Retrieve items");
		return itemRepository.findAll();
	}
	
//	@GetMapping("/items/id")
//	public Item retrieveItemById(@RequestParam(required = false) String id) {
//		logger.info("Retrieve item by id");
//		return itemRepository.getOne(Integer.valueOf(id));
//	}
	
	@PostMapping("/items")
	public Item createItem(@RequestBody Item item) {
		logger.info("Creating new item!");
		return itemRepository.save(item);
		
	}
}
