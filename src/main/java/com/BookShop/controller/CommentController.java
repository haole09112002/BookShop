package com.BookShop.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BookShop.entities.Comment;
import com.BookShop.payload.CommentRequest;
import com.BookShop.payload.CommentResponse;
import com.BookShop.services.CommentService;

@Controller
@RequestMapping("/bookshop/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping
	@ResponseBody
	public CommentResponse addComment(@RequestBody(required = true) CommentRequest commentRequest, Principal principal) {
		if(principal != null) {
			try {
				Comment comment = commentService.addComment(commentRequest, principal.getName());
				System.out.println(comment.getUser() == null ? true : false);
				CommentResponse commentResponse = new CommentResponse(comment.getId(), comment.getContent(), comment.getCreatedDate(), comment.getUser().getUsername(), comment.getUser().getFullName());
			System.out.println(comment.getId());
			return commentResponse;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		return null;
	}
}
