package ru.khananov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khananov.models.Book;

public interface BooksRepository extends JpaRepository<Book, Long> {
}
