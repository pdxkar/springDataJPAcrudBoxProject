package com.kroot.spring.datajpa.repository;

import com.kroot.spring.datajpa.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Specifies methods used to obtain and modify person related information
 * which is stored in the database.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
