package com.BookShop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.BookShop.services.impl.CustomAuthenticationProviderImpl;
import com.BookShop.services.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		http.csrf().disable().authorizeHttpRequests()
			
				.requestMatchers("bookshop/admin" ,"/bookshop/admin/**").hasAnyAuthority("ROLE_ADMIN")
				.anyRequest().permitAll()
			.and()
				.formLogin()
				.loginPage("/bookshop/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.loginProcessingUrl("/process-login")
				.defaultSuccessUrl("/bookshop").permitAll()
			.and()
				.logout()
				.logoutUrl("/bookshop/logout")
	            .logoutSuccessUrl("/bookshop")
//	            .deleteCookies("JSESSIONID")
				.permitAll() 
			.and()
	            .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(7*24*60*60)
			.and().exceptionHandling().accessDeniedPage("/bookshop/401");
			
		
		return http.build();
	}
	
//	@Bean
//	AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//		return daoAuthenticationProvider;
//		
//	}
	   @Bean
	    public CustomAuthenticationProviderImpl authProvider() {
		   CustomAuthenticationProviderImpl authenticationProvider = new CustomAuthenticationProviderImpl();
	        return authenticationProvider;
	    }

}
