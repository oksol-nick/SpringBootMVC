package com.greabit.dao;

import com.greabit.models.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (
        properties = {"jdbcUrl=jdbc:h2:mem:dt;DB_CLOSE_DELAY=-1"}
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookDaoImplTest {

    @Autowired
    BookDao bookDao;

    @BeforeEach
    public void beforeEach() {
        bookDao.deleteAll();
    }

    @Test
    public void contextCreated() {
    }

    @Test
    public void saveSavesDataToDbAndReturnEntityWithIn() {
        Book book = bookDao.save(new Book("SQL. Pocket Guide", "Alice Zhao", 320));
        assertThat(book.getId()).isNotBlank();
        assertThat(bookDao.findAll()).extracting("id").containsExactly(book.getId());
    }

    @Test
    void deleteAllDeleteAllData() {
        bookDao.save(new Book("SQL. Pocket Guide", "Alice Zhao", 320));
        assertThat(bookDao.findAll()).isNotEmpty();
        bookDao.deleteAll();
        assertThat(bookDao.findAll()).isEmpty();
    }

    @Test
    void findAllReturnedAllBooks() {
        assertThat(bookDao.findAll()).isEmpty();
        bookDao.save(new Book("SQL. Pocket Guide", "Alice Zhao", 320));
        assertThat(bookDao.findAll()).isNotEmpty();
    }

    @Test
    void getByIdThrowsRunTimeExceptionIfNoBookFound() {

        assertThatThrownBy(() -> bookDao.getById("1")).isExactlyInstanceOf(RuntimeException.class);


    }

    @Test
    void getByIdReturnsCorrectBook() {
        Book book = bookDao.save(new Book("SQL. Pocket Guide", "Alice Zhao", 320));
        Book bookOther =bookDao.save(new Book("Основы Java", "Николай Прохоренок", 768));

        assertThat(bookDao.getById(book.getId())).isNotNull().extracting("name").isEqualTo(book.getName());
    }

    @Test
    void updateUpdatesDataInDb() {
        Book book = bookDao.save(new Book("SQL. Pocket Guide", "Alice Zhao", 320));
        book.setName("New name");
        bookDao.update(book);
        assertThat(bookDao.getById(book.getId()).getName()).isEqualTo("New name");
    }

    @Test
    void updateThrowsExceptionOnUpdatingNotSaveBook() {

        assertThatThrownBy(() -> bookDao.update(new Book("SQL. Pocket Guide", "Alice Zhao", 320)))
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteDeleteCorrectData() {
        Book bookToKeep = bookDao.save(new Book("SQL. Pocket Guide", "Alice Zhao", 320));
        Book bookToDelete =bookDao.save(new Book("Основы Java", "Николай Прохоренок", 768));

        bookDao.delete(bookToDelete);
        assertThat(bookDao.getById(bookToKeep.getId())).isNotNull();
        assertThatThrownBy(() -> bookDao.getById(bookToDelete.getId())).isExactlyInstanceOf(RuntimeException.class);



    }

//    @Test
//    void findAll() {
//    }
//
//    @Test
//    void save() {
//    }
//
//    @Test
//    void gatById() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
}