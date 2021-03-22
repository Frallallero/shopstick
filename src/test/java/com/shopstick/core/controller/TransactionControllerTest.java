package com.shopstick.core.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shopstick.core.entity.Cart;
import com.shopstick.core.entity.CartItem;
import com.shopstick.core.entity.Item;
import com.shopstick.core.entity.Transaction;
import com.shopstick.core.repo.CartItemRepository;
import com.shopstick.core.repo.CartRepository;
import com.shopstick.core.repo.ItemRepository;
import com.shopstick.core.repo.TransactionRepository;
import com.shopstick.core.vo.PurchaseVO;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

	@Mock
	private CartRepository cartRepository;
	@Mock
	private CartItemRepository cartItemRepository;
	@Mock
	private TransactionRepository transactionRepository;
	@Mock
	private ItemRepository itemRepository;
	@InjectMocks
	private TransactionController transactionController;
	
	@Test
	void purchase_withInvalidCreditCard_returnsNull() {
		PurchaseVO purchase = new PurchaseVO();
		String response = transactionController.purchase(purchase);
		assertNull(response);
	}
	
	@Test
	void purchase_withValidCreditCard_returnsUniqueIdentifier() {
		PurchaseVO purchase = new PurchaseVO();
		purchase.setCardNumber("1234");
		purchase.setCvv("000");
		purchase.setCartId(1);
		
		Cart cart = new Cart();
		cart.setId(1);
		
		List<CartItem> cartItems = new ArrayList<>();
		Item item = new Item();
		item.setStockNumber(10);
		CartItem cartItem = new CartItem();
		cartItem.setItem(item);
		cartItem.setQuantity(4);
		cartItems.add(cartItem);
		
		Transaction transaction = new Transaction();
		
		when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
		when(cartItemRepository.findByCart(cart)).thenReturn(cartItems);
		when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));
		when(itemRepository.save(item)).thenReturn(null);
		
		String response = transactionController.purchase(purchase);
		assertNotNull(response);
	}
}
