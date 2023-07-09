package com.BookShop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BookShop.entities.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	Optional<CartItem> findByCartId(long id);
	
	Optional<CartItem> findByCartIdAndBookId(long cartId, long bookId);
	
	@Query("select c from CartItem c inner join c.cart cart where cart.user.username = :username and c.book.id = :bookId")
	CartItem  findByUsernameAndBookId(@Param("username") String username, @Param("bookId")long bookId);
}
