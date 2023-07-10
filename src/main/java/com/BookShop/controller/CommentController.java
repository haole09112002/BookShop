package com.BookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BookShop.entities.Comment;
import com.BookShop.payload.CommentRequest;
import com.BookShop.payload.CommentResponse;
import com.BookShop.payload.UserPrincipal;
import com.BookShop.services.CommentService;

@Controller
@RequestMapping("/bookshop/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping
	@ResponseBody
	public CommentResponse addComment(@RequestBody(required = true) CommentRequest commentRequest, Authentication authentication) {
		if(authentication != null) {
			try {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				Comment comment = commentService.addComment(commentRequest, userPrincipal.getUsername());
				CommentResponse commentResponse = new CommentResponse(comment.getId(), comment.getContent(), comment.getCreatedDate(), comment.getUser().getUsername(), comment.getUser().getFullName());
			return commentResponse;
			} catch (Exception e) {
				
			}
		}
		return null;
	}
}
