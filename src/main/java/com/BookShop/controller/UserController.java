package com.BookShop.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.BookShop.payload.UserInfoRequest;
import com.BookShop.services.UserService;

@Controller
@RequestMapping("/bookshop")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		UserInfoRequest newUser = new UserInfoRequest();
		model.addAttribute("newUser", newUser);
		return "register";
	}
	
	@GetMapping("/users/new")
	public String yourGetMethod(Model model) {
	    UserInfoRequest newUser = new UserInfoRequest(); // Khởi tạo đối tượng newUser

	    model.addAttribute("newUser", newUser);

	    return "register"; 
	}
	
	@PostMapping("/users/new")
	public String createNewUser(@ModelAttribute("newUser") UserInfoRequest newUser, Model model) {
	    if(userService.checkEmailAvailability(newUser.getEmail())){
	    	model.addAttribute("message", "Email đã tồn tại!");
	    	return "register";
	    }else if(!newUser.getCreatePassword().equals(newUser.getRepeatPassword())) {
	     	model.addAttribute("message", "Mật khẩu không trùng khớp!");
	    	return "register";
	    	
		}else {
			model.addAttribute("message", "Đăng ký thành công");
			model.addAttribute("user", userService.createUser(newUser));
	    	return "register";
		}
	    
	}
	


	@GetMapping("/admin/users")
	public String listUsers(Model model, Authentication authentication) {
		if(authentication != null) {
			model.addAttribute("users", userService.getAll());
			return "/admin/user";
		}
		else {
			return "redirect:/bookshop/401";
		}
	}
	
	@PostMapping("/admin/users/{username}/block")
	public ResponseEntity<?> blockUser(@PathVariable String username,Authentication authentication) {
		if(authentication != null) {
			userService.blockUser(username);
			return ResponseEntity.ok("Success");
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/admin/users/{username}/active")
	
	public  ResponseEntity<?> activeUser(@PathVariable String username,Authentication authentication) {
		if(authentication != null) {
			userService.activeUser(username);
			return ResponseEntity.ok("Success");
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@GetMapping("/admin/users/search")
	public String search(@RequestParam(required = true) String nonBlock, @RequestParam(required = false) String keyword ,Model model, Authentication authentication) {
		if(authentication != null) {
			System.out.println(userService.findByKeywordAndStatus(nonBlock, keyword).size()); 
			model.addAttribute("users", userService.findByKeywordAndStatus(nonBlock, keyword));
			return "/admin/user";
		}
		else {
			return "redirect:/bookshop/401";
		}
	}
	
	@PutMapping("/admin/users/{username}/give-admin")
	public ResponseEntity<?> giveAdmin(@PathVariable(required = true) String username ,Model model, Authentication authentication) {
		if(authentication != null) {
		 Boolean isCompete = userService.giveAdmin(username);
		 System.out.println(isCompete);
			return ResponseEntity.ok(isCompete);
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PutMapping("/admin/users/{username}/remove-admin")
	public ResponseEntity<?> remove(@PathVariable(required = true) String username ,Model model, Authentication authentication) {
		if(authentication != null) {
		 Boolean isCompete = userService.removeAdmin(username);
		 System.out.println(isCompete);
			return ResponseEntity.ok(isCompete);
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
