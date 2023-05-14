package ru.khananov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.khananov.dao.BookDAO;
import ru.khananov.dao.PersonDAO;
import ru.khananov.model.Book;
import ru.khananov.model.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.findAll());
        return "book/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookDAO.findById(id));
        model.addAttribute("person", new Person());
        model.addAttribute("people", personDAO.findAll());
        return "book/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "book/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/create";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookDAO.findById(id));
        return "book/edit";
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/edit";

        bookDAO.update(book);
        return "redirect:/books";
    }

    @PatchMapping("{id}/set/person")
    public String setPerson(@PathVariable("id") Long id, @ModelAttribute("person") Person person) {
        bookDAO.editPerson(id, person.getId());
        return "redirect:/books/" + id;
    }

    @PatchMapping("{id}/free/book")
    public String freeBook(@PathVariable("id") Long id) {
        bookDAO.deleteOwnerOfBook(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        bookDAO.remove(id);
        return "redirect:/books";
    }
}
