package com.shopstick.core.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shopstick.core.entity.ShopUser;
import com.shopstick.core.repo.CartItemRepository;
import com.shopstick.core.repo.ShopUserRepository;

@ExtendWith(MockitoExtension.class)
public class ShopUserControllerTest {

	@Mock
	private ShopUserRepository shopUserRepository;
	@Mock
	private CartItemRepository cartItemRepository;
	@InjectMocks
	private ShopUserController shopUserController;
	
	@Test
	void retrieveUsers_validData_returnUsersList() {
		List<ShopUser> suList = new ArrayList<>();
		ShopUser su = new ShopUser();
		su.setName("Test");
		suList.add(su);
		when(shopUserRepository.findAll()).thenReturn(suList);
		List<ShopUser> response = shopUserController.retrieveUsers();
		assertThat(response.get(0).getName()).isEqualTo("Test");
	}
	
	@Test
	void retrieveUserByCredentials_withInvalidCredentials_returnsEmptyShopUser() {
		ShopUser su = new ShopUser();
		when(shopUserRepository.findByUsernameAndPassword("user","pass")).thenReturn(su);
		String testUsername = "user";
		String testPassowrd = "password";
		
		ShopUser response;
		try {
			response = shopUserController.retrieveUserByCredentials(testUsername, testPassowrd);
		} catch (Exception e) {
			response = new ShopUser();
		}
		assertThat(response.getName()).isNull();
	}
	
	@Test
	void retrieveUserByCredentials_withValidCredentials_returnsShopUser() {
		ShopUser su = new ShopUser();
		su.setName("testName");
		when(shopUserRepository.findByUsernameAndPassword("user","pass")).thenReturn(su);
		String testUsername = "user";
		String testPassowrd = "pass";
		
		ShopUser response;
		try {
			response = shopUserController.retrieveUserByCredentials(testUsername, testPassowrd);
		} catch (Exception e) {
			response = new ShopUser();
		}
		assertThat(response.getName()).isEqualTo("testName");
	}
}
