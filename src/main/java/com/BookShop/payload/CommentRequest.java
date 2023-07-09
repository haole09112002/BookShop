package com.BookShop.payload;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CommentRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String comment;
	private Long bookId;
}
