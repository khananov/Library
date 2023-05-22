package ru.khananov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khananov.models.Person;

public interface PeopleRepository extends JpaRepository<Person, Long> {
}
