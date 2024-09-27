package com.greabit.dao;

import com.greabit.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Component
public class BookDaoImpl implements BookDao {

       private final DataSource dataSource;
    @Autowired

    public BookDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try(
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM book")
                ) {
                    while (resultSet.next()) {
                        Book book =new Book(resultSet.getString(1), resultSet.getString(2),
                                resultSet.getString(3), resultSet.getInt(4));
                        books.add(book);
                    }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public Book save(Book book) {

            try(Connection connection = dataSource.getConnection();

                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO book (name, author, pages) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, book.getName());
                preparedStatement.setString(2, book.getAuthor());
                preparedStatement.setInt(3,book.getPages());
                preparedStatement.executeUpdate();
                try(
                        ResultSet resultSet = preparedStatement.getGeneratedKeys();
                        ) {
                    resultSet.next();
                    book.setId(resultSet.getString(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return book;
    }

    @Override
    public Book getById(String  bookId) {

        try(Connection connection = dataSource.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book WHERE book_id = ?",
                    Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, Integer.parseInt(bookId));

            try(
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                if(!resultSet.next()) {
                    throw new RuntimeException(String.format("Book with id %s was not found", bookId));
                }
                return new Book(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book update(Book book) {
        if(Objects.isNull(book.getId())) {
            throw new RuntimeException("Can't update unsaved book");
        }
        try(Connection connection = dataSource.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE book SET name = ?, author = ?, pages = ? WHERE book_id = ?")) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3,book.getPages());
            preparedStatement.setString(4,book.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return book;
    }

    @Override
    public void delete(Book book) {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM book WHERE book_id = ?");
        ) {
            preparedStatement.setString(1, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try(
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ) {
            statement.executeUpdate("TRUNCATE TABLE book");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
