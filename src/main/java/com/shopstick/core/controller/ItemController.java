package com.shopstick.core.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopstick.core.entity.Item;
import com.shopstick.core.repo.ItemRepository;
import com.shopstick.core.vo.ItemVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/core-rest")
@RequiredArgsConstructor
public class ItemController {

	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	private final ItemRepository itemRepository;

	
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
	
	@GetMapping("/items/categories/{categoryName}")
	public List<Item> retrieveItems(@PathVariable String categoryName) {
		logger.info("Retrieve items by category");
		return itemRepository.findByCategory(categoryName);
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
}
