package com.shopstick.core.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shopstick.core.entity.Cart;
import com.shopstick.core.entity.Item;
import com.shopstick.core.entity.ShopUser;
import com.shopstick.core.repo.CartItemRepository;
import com.shopstick.core.repo.CartRepository;
import com.shopstick.core.repo.ItemRepository;
import com.shopstick.core.repo.ShopUserRepository;
import com.shopstick.core.repo.TransactionRepository;
import com.shopstick.core.vo.CartItemVO;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {
	
	@Mock
	private ShopUserRepository shopUserRepository;
	@Mock
	private ItemRepository itemRepository;
	@Mock
	private CartRepository cartRepository;
	@Mock
	private CartItemRepository cartItemRepository;
	@Mock
	private TransactionRepository transactionRepository;

	
	@InjectMocks
	private CartController cartController;
	
	@Test
	void addItemToCart_tooManyItemsRequested_throwException() {
		
		CartItemVO cartItemVO = new CartItemVO();
		cartItemVO.setCartId(1);
		cartItemVO.setItemId(2);
		cartItemVO.setQuantity(5);
		cartItemVO.setUserId(1);

		ShopUser shopUser = new ShopUser();
		shopUser.setId(1);
		
		Cart cart = new Cart();
		cart.setId(3);
		
		Item item = new Item();
		item.setId(2);
		item.setStockNumber(3);
		
		when(shopUserRepository.findById(1)).thenReturn(Optional.of(shopUser));
		when(cartRepository.findByShopUser(shopUser)).thenReturn(cart);
		when(itemRepository.findById(2)).thenReturn(Optional.of(item));
		
		Assertions.assertThrows(Exception.class, () -> {
			cartController.addItemToCart(cartItemVO);
		});
	}
	
	@Test
	void retrieveCarts_validData_returnCartsList() {
		List<Cart> cartList = new ArrayList<>();
		Cart cart = new Cart();
		cart.setId(1);
		cartList.add(cart);
		when(cartRepository.findAll()).thenReturn(cartList);
		List<Cart> response = cartController.retrieveCarts();
		assertThat(response.get(0).getId()).isEqualTo(1);
	}
	
	@Test
	void retrieveCartItems_validData_returnItemsList() {
		List<Item> itemList = new ArrayList<>();
		Item item = new Item();
		item.setId(1);
		itemList.add(item);
		when(cartItemRepository.retrieveItemsByCartId(1)).thenReturn(itemList);
		List<Item> response = cartController.retrieveCartItems(1);
		assertThat(response.get(0).getId()).isEqualTo(1);
	}
	
	@Test
	void retrieveCartItems_whenNotFound_returnEmptyList() {
		when(cartItemRepository.retrieveItemsByCartId(1)).thenReturn(new ArrayList<>());
		List<Item> response = cartController.retrieveCartItems(1);
		assertEquals(response, new ArrayList<Item>());
	}
	
}
