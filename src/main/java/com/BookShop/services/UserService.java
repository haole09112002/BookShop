package com.BookShop.services;

import java.util.List;

import com.BookShop.entities.User;
import com.BookShop.payload.UserInfoRequest;
import com.BookShop.payload.UserRequest;

public interface UserService {
	List<User> getAll();
	
	User createUser(UserInfoRequest newUser);
	
	boolean checkUsernameAvailability(String username);
	
	boolean checkEmailAvailability(String email);
	
	boolean giveAdmin(String username);

	boolean removeAdmin(String username);
	
	boolean blockUser(String username);
	
	boolean activeUser(String username);
	
	List<User> findByKeywordAndStatus(String accountNonBlock, String keyword);
}
