package com.BookShop.services;

public interface EmailService {

	void sendSimpleMessage(String to, String subject, String content);

}
