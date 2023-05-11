package ru.khananov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.khananov.model.Book;
import ru.khananov.model.Person;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?",
                new BeanPropertyRowMapper<>(Book.class), id).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (name, author, year, person_id) VALUES (?, ?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear(), book.getPersonId());
    }

    public void update(Book updateBook) {
        jdbcTemplate.update("UPDATE book SET (name, author, year) = (?, ?, ?) WHERE id = ?",
                updateBook.getName(), updateBook.getAuthor(), updateBook.getYear(), updateBook.getPersonId());
    }

    public void remove(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }
}
