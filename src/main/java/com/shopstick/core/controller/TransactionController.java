package com.shopstick.core.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopstick.core.entity.Cart;
import com.shopstick.core.entity.CartItem;
import com.shopstick.core.entity.Item;
import com.shopstick.core.entity.Transaction;
import com.shopstick.core.repo.CartItemRepository;
import com.shopstick.core.repo.CartRepository;
import com.shopstick.core.repo.ItemRepository;
import com.shopstick.core.repo.TransactionRepository;
import com.shopstick.core.vo.PurchaseVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/core-rest")
@RequiredArgsConstructor
public class TransactionController {

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final TransactionRepository transactionRepository;
	private final ItemRepository itemRepository;
	
	
	@PostMapping("/transactions/confirm")
	public String purchase(@RequestBody PurchaseVO purchase) {
		logger.info("Purchase");
		String response = null;
//		Check creditCard
		if(purchase!=null && purchase.getCardNumber()!=null && purchase.getCvv()!=null) {
			if(!(purchase.getCardNumber().equalsIgnoreCase("1234") &&
					purchase.getCvv().equalsIgnoreCase("000"))) {
				return null;
			}

//			Remove quantity from stock and delete the cart items
			Optional<Cart> opCart = cartRepository.findById(purchase.getCartId());
			if(opCart.isPresent()) {
				Cart cart = opCart.get();
				List<CartItem> cartItems = cartItemRepository.findByCart(cart);
				if(!cartItems.isEmpty()) {
					for(CartItem cartItem : cartItems) {
						Item item = cartItem.getItem();
						item.setStockNumber(item.getStockNumber()-cartItem.getQuantity());
						itemRepository.save(item);
//						TODO add and manage cart status
						cartItemRepository.delete(cartItem);
					}
				}

//				Update transaction status
				Optional<Transaction> opTransaction = transactionRepository.findById(cart.getId());
				if(opTransaction.isPresent()) {
					Transaction transaction = opTransaction.get();
					transaction.setStatusId(2);
				}
			}

//			TODO just to have this working everytime. Should be moved in the if opTransaction.isPresent()
			UUID corrId = UUID.randomUUID();
			response = corrId.toString();
		}
		return response;
	}
}
