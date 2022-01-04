package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
