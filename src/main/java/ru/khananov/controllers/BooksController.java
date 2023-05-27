package ru.khananov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.khananov.models.Book;
import ru.khananov.models.Person;
import ru.khananov.services.BooksService;
import ru.khananov.services.PeopleService;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(name = "page", required = false) Integer page,
                        @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(name = "sort_by_year", required = false) boolean isSorting,
                        Model model) {
        model.addAttribute("books", booksService.findAll(page, booksPerPage, isSorting));
        return "book/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("owner", booksService.getOwnerBook(id));
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

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        return "book/edit";
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("{id}/assign")
    public String assign(@PathVariable("id") Long id, @ModelAttribute("person") Person person) {
        booksService.editPerson(id, person.getId());
        return "redirect:/books/";
    }

    @PatchMapping("{id}/release")
    public String release(@PathVariable("id") Long id) {
        booksService.deleteOwnerOfBook(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        booksService.remove(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "query", required = false) String query, Model model) {
        model.addAttribute("books", booksService.findByName(query));
        return "book/search";
    }
}