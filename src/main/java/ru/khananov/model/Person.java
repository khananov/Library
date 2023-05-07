package ru.khananov.model;

import java.util.Date;

public class Person {
    private Long id;
    private String fio;
    private Date birth_date;

    public Person() {
    }

    public Person(String fio, Date birth_date) {
        this.fio = fio;
        this.birth_date = birth_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }
}
