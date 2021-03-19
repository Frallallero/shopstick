package com.shopstick.core.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopstick.core.entity.Cart;
import com.shopstick.core.entity.Item;
import com.shopstick.core.entity.Role;
import com.shopstick.core.entity.ShopUser;
import com.shopstick.core.entity.Transaction;
import com.shopstick.core.repo.CartRepository;
import com.shopstick.core.repo.ItemRepository;
import com.shopstick.core.repo.RoleRepository;
import com.shopstick.core.repo.ShopUserRepository;
import com.shopstick.core.repo.TransactionRepository;
import com.shopstick.core.vo.AddCartItem;

@RestController
@RequestMapping("/core-rest")
public class CoreController {

	private static final Logger logger = LoggerFactory.getLogger(CoreController.class);

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ShopUserRepository shopUserRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;

	
//	ROLES
	@GetMapping("/roles")
	public List<Role> retrieveRoles() {
		logger.info("Retrieve roles");
		return roleRepository.findAll();
	}
	
//	USERS
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
	
	@GetMapping("/users/{name}")
	public List<ShopUser> retrieveUserByName(@PathVariable String name) {
		logger.info("Retrieve user by name");
		return shopUserRepository.getUserByName(name);
	}
	
//	ITEMS
	@GetMapping("/items")
	public List<Item> retrieveItems() {
		logger.info("Retrieve items");
		return itemRepository.findAll();
	}
	
	@GetMapping("/items/{id}")
	public Item retrieveItemById(@PathVariable Integer id) {
		logger.info("Retrieve item by id");
		return itemRepository.getOne(id);
	}
	
	@PostMapping("/items")
	public Item createItem(@RequestBody Item item) {
		logger.info("Creating new item!");
		return itemRepository.save(item);
	}
	
	@PatchMapping("/items/stock-item")
	public boolean updateItemStock(
			@RequestParam(value = "itemId", required = true) Integer itemId,
			@RequestParam(value = "quantity", required = true) Integer quantity) {
		logger.info("Stock items");
		boolean response = false;
		Optional<Item> optionalItem = itemRepository.findById(itemId);
		if(optionalItem.isPresent()) {
			Item item = optionalItem.get();
			item.setStockNumber(quantity);
			itemRepository.save(item);
			response = true;
		}
		return response;
	}
	
//	CART
	@PostMapping("/carts/add-item")
	public Cart addItemToCart(
			@RequestBody(required = true) AddCartItem addCartItem) {
		logger.info("Add items to cart!");
		Cart cart;
		
		Optional<ShopUser> user = shopUserRepository.findById(addCartItem.getUserId());
		if(user!=null) {
			cart = cartRepository.findByShopUser(user.get());
			if(cart==null) {
				cart = new Cart();
				
//				if new Cart create a new Order
				Transaction transaction = new Transaction();
				transaction.setShopUser(user.get());
				transaction.setStatusId(1);
				
				transactionRepository.save(transaction);
				cart.setTransaction(transaction);
			}
			cart.setItemId(addCartItem.getItemId());
			cart.setQuantity(addCartItem.getQuantity());
//			cartRepository.save(cart);
		}
		return null;
	}
	
}
