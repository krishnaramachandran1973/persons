package com.cts.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entities.Person;
import com.cts.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/persons")
public class PersonController {

	private final PersonRepository personRepository;

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Person>> getPersons() {
		log.info("Getting all persons");
		return ResponseEntity.ok(personRepository.findAll());
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> removePerson(@PathVariable Long id) {
		log.info("Deleting Person with id {}", id);
		personRepository.deleteById(id);
		return ResponseEntity.ok()
				.build();
	}

	@PostMapping
	public ResponseEntity<?> addPerson(@RequestBody Person person) {
		log.info("Adding person {}", person);
		personRepository.save(person);
		return ResponseEntity.ok()
				.build();
	}

}
