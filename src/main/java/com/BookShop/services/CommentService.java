package com.BookShop.services;

import java.util.List;

import com.BookShop.entities.Comment;
import com.BookShop.payload.CommentRequest;

public interface CommentService {
	List<Comment> findCommentsByBookId(Long id);

	Comment addComment(CommentRequest commentRequest, String username);
}
