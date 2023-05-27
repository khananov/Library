package ru.khananov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khananov.models.Book;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Long> {
    List<Book> findByNameStartingWith(String name);
}
