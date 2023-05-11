package ru.khananov.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Book {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String author;

    @NotEmpty
    @Min(0)
    private int year;
    private Long personId;

    public Book() {
    }

    public Book(String name, String author, int year, Long personId) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.personId = personId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", person_id=" + personId +
                '}';
    }
}
