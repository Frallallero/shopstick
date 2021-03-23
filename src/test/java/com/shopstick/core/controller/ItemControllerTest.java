package com.shopstick.core.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shopstick.core.entity.Item;
import com.shopstick.core.repo.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

	@Mock
	private ItemRepository itemRepository;
	@InjectMocks
	private ItemController itemController;
	
	@Test
	void retrieveItems_withNodata_returnEmptyList() {
		when(itemRepository.findAll()).thenReturn(new ArrayList<Item>());
		List<Item> response = itemController.retrieveItems();
		assertThat(response).isEmpty();
	}
	
	@Test
	void retrieveItemById_withNodata_returnEmptyList() {
		Item item = new Item();
		item.setId(1);
		when(itemRepository.getOne(1)).thenReturn(item);
		Item response = itemController.retrieveItemById(1);
		assertThat(response).isEqualTo(item);
	}
	
	@Test
	void updateItemStock_withValidData_setStockAndReturnsTrue() {
		Item item = new Item();
		item.setId(1);
		when(itemRepository.findById(1)).thenReturn(Optional.of(item));
		boolean response = itemController.updateItemStock(1,4);
		assertThat(response).isTrue();
	}
}
