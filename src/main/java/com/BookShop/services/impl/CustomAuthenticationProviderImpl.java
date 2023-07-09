package com.BookShop.services.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
@Service
public class CustomAuthenticationProviderImpl implements AuthenticationProvider{

	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
//		LOGGER.info("DEBUG: " +password);
		UserDetails user = userDetailsService.loadUserByUsername(username);
		return checkPassword(user, password);
	}
	private Authentication checkPassword(UserDetails user, String rawPassword) {
		System.out.println(user.getPassword());
		System.out.println(rawPassword);
		if (Objects.equals(rawPassword, user.getPassword())) {
			return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
					user.getAuthorities());
		} else {
			throw new BadCredentialsException("Bad credentials");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}
