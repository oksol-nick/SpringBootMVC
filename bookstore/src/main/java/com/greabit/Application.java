package com.greabit;

import com.greabit.models.Book;
import com.greabit.models.BooksStorage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		BooksStorage.getBooks().add(new Book(UUID.randomUUID().toString(),"Spring in Action", "Craig Walas", 544));
		BooksStorage.getBooks().add(new Book(UUID.randomUUID().toString(),"The Complete Reference Java. Twelfth Edition", "Herbert Schildt", 1344));
		BooksStorage.getBooks().add(new Book(UUID.randomUUID().toString(),"Classic Computer Science Problems in Java", "David Kopec", 288));
		SpringApplication.run(Application.class, args);
	}


}
