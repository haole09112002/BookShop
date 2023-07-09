package com.BookShop.services.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.BookShop.entities.User;
import com.BookShop.payload.UserPrincipal;
import com.BookShop.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	public static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails  loadUserByUsername(String username){
		User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Khong tim thay username"));
		user.setRoles( user.getRoles());
		
	
		return 	new UserPrincipal().create(user);
	}
}
