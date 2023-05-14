package ru.khananov.model;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

public class Person {
    private Long id;

    @NotEmpty
    private String fio;

    private Date birthDate;

    public Person() {
    }

    public Person(String fio, Date birthDate) {
        this.fio = fio;
        this.birthDate = birthDate;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birth_date) {
        this.birthDate = birth_date;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", birth_date=" + birthDate +
                '}';
    }
}
