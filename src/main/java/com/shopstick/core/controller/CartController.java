package com.shopstick.core.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopstick.core.entity.Cart;
import com.shopstick.core.entity.CartItem;
import com.shopstick.core.entity.Item;
import com.shopstick.core.entity.ShopUser;
import com.shopstick.core.entity.Transaction;
import com.shopstick.core.repo.CartItemRepository;
import com.shopstick.core.repo.CartRepository;
import com.shopstick.core.repo.ItemRepository;
import com.shopstick.core.repo.ShopUserRepository;
import com.shopstick.core.repo.TransactionRepository;
import com.shopstick.core.vo.CartItemVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/core-rest")
@RequiredArgsConstructor
public class CartController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	private final ShopUserRepository shopUserRepository;
	private final ItemRepository itemRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final TransactionRepository transactionRepository;

	
	@PostMapping("/carts/add-item")
	public Cart addItemToCart(
			@RequestBody(required = true) CartItemVO cartItemToAdd) throws Exception {
		logger.info("Add items to cart!");
		Cart cart;
		
		Optional<ShopUser> user = shopUserRepository.findById(cartItemToAdd.getUserId());
		if(user.isPresent()) {
			cart = cartRepository.findByShopUser(user.get());
			Optional<Item> optionalItem = itemRepository.findById(cartItemToAdd.getItemId());
			if(optionalItem.isPresent()) {
				Item item = optionalItem.get();
				
//				If quantity to add to cart > item availability return
				if(item.getStockNumber()<cartItemToAdd.getQuantity()) {
					throw new Exception();
				}

				CartItem cartItem;
				if(cart!=null) {
//					if the item is already in the cart -> update the quantity
					cartItem = cartItemRepository.findByCartAndItem(cart, item);
					if(cartItem==null) {
						cartItem = new CartItem();
						saveCartItem(cartItem, cart, item, cartItemToAdd.getQuantity());
					} else {
						cartItem.setQuantity(cartItemToAdd.getQuantity());
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
					saveCartItem(cartItem, cart, item, cartItemToAdd.getQuantity());
				}
				cartRepository.save(cart);
			} else {
				// TODO ITEM NOT FOUND
			}
		}
		return null;
	}
	
	@Transactional
	@PostMapping("/carts/delete-item")
	public boolean deleteItemFromCart(
			@RequestBody(required = false) CartItemVO cartItemToDelete) {
		
		logger.info("Delete item from cart!");
		boolean deleted = false;

		Optional<Cart> optionalCart = cartRepository.findById(cartItemToDelete.getCartId());
		Optional<Item> optionalItem = itemRepository.findById(cartItemToDelete.getItemId());
		if(optionalCart.isPresent() && optionalItem.isPresent()) {
			CartItem cartItem = cartItemRepository.findByCartAndItem(optionalCart.get(), optionalItem.get());
			cartItemRepository.delete(cartItem);
			deleted = true;
		}
		
		return deleted;
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
}
