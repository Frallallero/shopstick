package com.shopstick.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopstick.core.entity.Cart;
import com.shopstick.core.entity.ShopUser;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	/**
     * Retrieve Cart by ShopUser
     * @param shopUser
     * @return cart
     */
    @Query(value="SELECT c "
    		+ " FROM Cart c "
    		+ " INNER JOIN c.transaction t "
    		+ " WHERE t.shopUser = :shopUser")
 	public Cart findByShopUser(@Param("shopUser") ShopUser shopUser);
    
}
