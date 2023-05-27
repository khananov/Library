package ru.khananov.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khananov.models.Book;
import ru.khananov.models.Person;
import ru.khananov.repositories.BooksRepository;
import ru.khananov.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(Integer page, Integer booksPerPage, boolean isSorting) {
        if (page != null && booksPerPage != null) {
            if (isSorting) {
                return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
            } else {
                return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
            }
        } else {
            if (isSorting) {
                return booksRepository.findAll(Sort.by("year"));
            } else {
                return booksRepository.findAll();
            }
        }
    }

    public List<Book> findByName(String name) {
        return (name != null) ? booksRepository.findByNameStartingWith(name) : new ArrayList<>();
    }

    public Book findById(Long id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Long id, Book updateBook) {
        updateBook.setId(id);
        booksRepository.save(updateBook);
    }

    @Transactional
    public void deleteOwnerOfBook(Long id) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()) {
            Hibernate.initialize(book.get().getOwner());
            book.get().setOwner(null);
        }
    }

    @Transactional
    public void editPerson(Long bookId, Long personId) {
        Optional<Book> book = booksRepository.findById(bookId);
        Optional<Person> person = peopleRepository.findById(personId);
        if (book.isPresent() && person.isPresent()) {
            Hibernate.initialize(book.get().getOwner());
            Hibernate.initialize(person.get().getBooks());
            book.get().setOwner(person.get());
        }
    }

    public Person getOwnerBook(Long id) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent() && book.get().getOwner() != null) {
           Hibernate.initialize(book.get().getOwner());
           return book.get().getOwner();
        } else
            return new Person();
    }

    @Transactional
    public void remove(Long id) {
        booksRepository.deleteById(id);
    }
}