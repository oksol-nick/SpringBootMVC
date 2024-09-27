package com.greabit;

import com.greabit.models.Book;
//import com.greabit.models.BooksStorage;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
public class Application {

	@Bean
	public DataSource h2DataSource(@Value("${jdbcUrl}") String jdbcUrl) {
		JdbcDataSource jdbcDataSource = new JdbcDataSource();
		jdbcDataSource.setURL(jdbcUrl);
		jdbcDataSource.setUser("user");
		jdbcDataSource.setPassword("password");
		return jdbcDataSource;
	}

//	@Bean
//	public CommandLineRunner cmd(DataSource dataSource) {
//		return args -> {
//			try (InputStream inputStream   = this.getClass().getResource("/initial.sql").openStream()) {
//				String sql = new String(inputStream.readAllBytes());
//				try (
//						Connection connection = dataSource.getConnection();
//						Statement statement = connection.createStatement();) {
//
//					statement.executeUpdate(sql);
//
//					try(PreparedStatement preparedStatement = connection.prepareStatement(
//							"INSERT INTO book (author, name, pages) VALUES (?, ?, ?)")) {
//						preparedStatement.setString(1,"Основы Java");
//						preparedStatement.setString(2,"Николай Прохоренок");
//						preparedStatement.setInt(3,768);
//						preparedStatement.executeUpdate();
//					}
//
//					System.out.println("Printing books from db...");
//					ResultSet resultSet = statement.executeQuery("SELECT book_id, name, author, pages FROM book");
//					while (resultSet.next()) {
//						Book book = new Book(resultSet.getString(1), resultSet.getString(2),
//								resultSet.getString(3), resultSet.getInt(4));
//						System.out.println(book);
//					}
//				}
//			}
//		};
//	}

	public static void main(String[] args) {

//		BooksStorage.getBooks().add(new Book(UUID.randomUUID().toString(),"Spring in Action", "Craig Walas", 544));
//		BooksStorage.getBooks().add(new Book(UUID.randomUUID().toString(),"The Complete Reference Java. Twelfth Edition", "Herbert Schildt", 1344));
//		BooksStorage.getBooks().add(new Book(UUID.randomUUID().toString(),"Classic Computer Science Problems in Java", "David Kopec", 288));
		SpringApplication.run(Application.class, args);
	}


}
