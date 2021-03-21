package com.shopstick.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopstick.core.entity.Cart;
import com.shopstick.core.entity.CartItem;
import com.shopstick.core.entity.Item;
import com.shopstick.core.vo.UserItemVO;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	@Query(value = "SELECT i FROM CartItem ci "
			+ " INNER JOIN ci.item i "
			+ " WHERE ci.id=:id")
    public List<Item> retrieveItemsByCartId(@Param("id") Integer id);
	
	@Query(value = "SELECT "
    	    + "new com.shopstick.core.vo.UserItemVO(ci) "
    		+ " FROM CartItem ci "
			+ " INNER JOIN ci.item i "
			+ " INNER JOIN ci.cart c "
			+ " INNER JOIN c.transaction t "
			+ " INNER JOIN t.shopUser s "
			+ " WHERE s.id=:customerId "
			+ " AND s.role=2")
    public List<UserItemVO> retrieveCustomerCart(@Param("customerId") Integer customerId);
	
    public List<CartItem> findByCart(Cart cart);

    public CartItem findByCartAndItem(Cart cart, Item item);
}
