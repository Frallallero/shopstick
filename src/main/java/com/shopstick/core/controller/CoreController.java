package com.shopstick.core.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import com.shopstick.core.entity.CartItem;
import com.shopstick.core.entity.Item;
import com.shopstick.core.entity.Role;
import com.shopstick.core.entity.ShopUser;
import com.shopstick.core.entity.Transaction;
import com.shopstick.core.repo.CartItemRepository;
import com.shopstick.core.repo.CartRepository;
import com.shopstick.core.repo.ItemRepository;
import com.shopstick.core.repo.RoleRepository;
import com.shopstick.core.repo.ShopUserRepository;
import com.shopstick.core.repo.TransactionRepository;
import com.shopstick.core.vo.AddCartItem;
import com.shopstick.core.vo.ItemVO;
import com.shopstick.core.vo.PurchaseVO;
import com.shopstick.core.vo.UserItemVO;

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
	private CartItemRepository cartItemRepository;
	
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
	
//	ITEMS
	@PostMapping(value = "/items")
	public Item createItem(@RequestBody ItemVO item) {
		logger.info("Creating new item!");
		return itemRepository.save(new Item(item));
	}
	
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
		if(user.isPresent()) {
			cart = cartRepository.findByShopUser(user.get());
			Optional<Item> optionalItem = itemRepository.findById(addCartItem.getItemId());
			if(optionalItem.isPresent()) {
				Item item = optionalItem.get();
				
//				If quantity to add to cart > item availability return
				if(item.getStockNumber()<addCartItem.getQuantity()) {
//					TODO manage exception
					return null;
				}

				CartItem cartItem;
				if(cart!=null) {
//					if the item is already in the cart -> update the quantity
					cartItem = cartItemRepository.findByCartAndItem(cart, item);
					if(cartItem==null) {
						cartItem = new CartItem();
						saveCartItem(cartItem, cart, item, addCartItem.getQuantity());
					} else {
						cartItem.setQuantity(addCartItem.getQuantity());
					}
				} else {
					cart = new Cart();
					cartItem = new CartItem();

//					if new Cart create a new Order
					Transaction transaction = new Transaction();
					transaction.setShopUser(user.get());
					transaction.setStatusId(1);

					transactionRepository.save(transaction);
					cart.setTransaction(transaction);
					saveCartItem(cartItem, cart, item, addCartItem.getQuantity());
				}
				cartRepository.save(cart);
			} else {
				// TODO ITEM NOT FOUND
			}
		}
		return null;
	}
	
	private void saveCartItem(CartItem cartItem, Cart cart, Item item, Integer quantity) {
		cartItem.setCart(cart);
		cartItem.setItem(item);
		cartItem.setQuantity(quantity);
		cart.getCartItems().add(cartItemRepository.save(cartItem));
	}
	
	@GetMapping("/carts")
	public List<Cart> retrieveCarts() {
		logger.info("Retrieve carts");
		List<Cart> carts = cartRepository.findAll();
		return carts;
	}
	
	@GetMapping("/carts/{id}/items")
	public List<Item> retrieveCartItems(@PathVariable Integer id) {
		logger.info("Retrieve cart items");
		List<Item> items = cartItemRepository.retrieveItemsByCartId(id);
		return items;
	}
	
//	TRANSACTION
	@PostMapping("/transactions/confirm")
	public String purchase(@RequestBody PurchaseVO purchase) {
		logger.info("Purchase");

//		Check creditCard
		if(!(purchase.getCardNumber().equalsIgnoreCase("1234") &&
		   purchase.getCvv().equalsIgnoreCase("000"))) {
			return null;
		}
		
//		Remove quantity from stock
		Optional<Cart> opCart = cartRepository.findById(purchase.getCartId());
		if(opCart.isPresent()) {
			Cart cart = opCart.get();
			List<CartItem> cartItems = cartItemRepository.findByCart(cart);
			if(!cartItems.isEmpty()) {
				for(CartItem cartItem : cartItems) {
					Item item = cartItem.getItem();
					item.setStockNumber(item.getStockNumber()-cartItem.getQuantity());
				}
			}
			
//			Update transaction status
			Optional<Transaction> opTransaction = transactionRepository.findById(cart.getId());
			if(opTransaction.isPresent()) {
				Transaction transaction = opTransaction.get();
				transaction.setStatusId(2);
			}
		}
		
		UUID corrId = UUID.randomUUID();
		return corrId.toString();
	}
}
