package com.BookShop.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BookShop.entities.Book;
import com.BookShop.entities.Comment;
import com.BookShop.entities.User;
import com.BookShop.payload.CommentRequest;
import com.BookShop.repositories.BookRepository;
import com.BookShop.repositories.CommentRepository;
import com.BookShop.repositories.UserRepository;
import com.BookShop.services.CommentService;

import jakarta.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<Comment> findCommentsByBookId(Long id) {
		 return commentRepository.findByBookId(id);
	}
	
	@Override
	@Transactional
	public Comment addComment(CommentRequest commentRequest, String username) {
		User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException());
		Book book = bookRepository.findById(commentRequest.getBookId()).orElseThrow(()-> new RuntimeException());
		
		Comment comment = new Comment();
		comment.setBook(book);
		comment.setUser(user);
		comment.setCreatedDate(LocalDateTime.now());
		comment.setContent(commentRequest.getComment());
		
		return commentRepository.save(comment);
	}

}
