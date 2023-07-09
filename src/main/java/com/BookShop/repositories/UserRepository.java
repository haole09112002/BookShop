package com.BookShop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.BookShop.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	@Query("select u from User u where (LOWER(u.username) like CONCAT('%', :keyword, '%')"
			+ " or  LOWER(u.phone) like CONCAT('%', :keyword, '%'))"
			+ " and u.accountNonBlock =  :accountNonBlock ")
	List<User> findByUserNameOrEmailOrPhoneAndStatus(String keyword, boolean accountNonBlock);

	@Query("select u from User u where LOWER(u.username) like CONCAT('%', :keyword, '%')"
			+ " or  LOWER(u.phone) like CONCAT('%', :keyword, '%')")
	List<User> findByUserNameOrEmailOrPhone(String keyword);
}
