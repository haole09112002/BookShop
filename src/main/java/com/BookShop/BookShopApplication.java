package com.BookShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.BookShop.config.FileStorageProperties;



@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class BookShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookShopApplication.class, args);
	}

}
