package com.BookShop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.BookShop.services.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void sendSimpleMessage(String to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();
	     
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
		    helper.setText(content, true);
			mailSender.send(message);	
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
