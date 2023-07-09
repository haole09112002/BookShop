package com.BookShop.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.BookShop.entities.RoleEnum;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class UserAuthenticationSuccessHandler  implements AuthenticationSuccessHandler{

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		boolean hasUserRole = false;
		boolean hasAdminRole = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals(RoleEnum.ROLE_USER.toString())) {
				hasUserRole = true;
				break;
			} else if (grantedAuthority.getAuthority().equals(RoleEnum.ROLE_ADMIN.toString())) {
				hasAdminRole = true;
				break;
			}
		}

		if (hasUserRole) {
			redirectStrategy.sendRedirect(request, response, "/bookshop");
		} else if (hasAdminRole) {
			redirectStrategy.sendRedirect(request, response, "/bookshop/admin");
		} else {
			throw new IllegalStateException();
		}
		
	}

}
