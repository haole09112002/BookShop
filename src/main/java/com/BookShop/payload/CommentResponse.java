package com.BookShop.payload;

import java.time.LocalDateTime;

import com.BookShop.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponse {
	private long id;
	private String comment;
	private LocalDateTime createdDate;
	private String username;
	private String userFullname;
}
