package com.shopstick.core.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopstick.core.entity.Role;
import com.shopstick.core.entity.ShopUser;
import com.shopstick.core.repo.CartItemRepository;
import com.shopstick.core.repo.RoleRepository;
import com.shopstick.core.repo.ShopUserRepository;
import com.shopstick.core.vo.UserItemVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/core-rest")
@RequiredArgsConstructor
public class ShopUserController {

	private static final Logger logger = LoggerFactory.getLogger(ShopUserController.class);

	private final RoleRepository roleRepository;
	private final ShopUserRepository shopUserRepository;
	private final CartItemRepository cartItemRepository;
	
	
	@GetMapping("/roles")
	public List<Role> retrieveRoles() {
		logger.info("Retrieve roles");
		return roleRepository.findAll();
	}
	
	@PostMapping("/users")
	public ShopUser createUser(@RequestBody ShopUser user) {
		logger.info("Creating new user!");
		return shopUserRepository.save(user);
	}
	
	@GetMapping("/users")
	public List<ShopUser> retrieveUsers() {
		logger.info("Retrieve users");
		return shopUserRepository.findAll();
	}
	
	@GetMapping("/users/credentials/{username}/{password}")
	public ShopUser retrieveUserByCredentials(
			@PathVariable("username") String username,
			@PathVariable("password") String password) throws Exception {
		logger.info("Retrieve user by credentials");
		ShopUser shopUser = shopUserRepository.findByUsernameAndPassword(username, password);
		return shopUser;
	}
	
	@GetMapping("/users/{id}/cart-items")
	public List<UserItemVO> retrieveCustomerCart(@PathVariable Integer id) {
		logger.info("Retrieve customer cart");
		List<UserItemVO> items = cartItemRepository.retrieveCustomerCart(id);
		return items;
	}

}
