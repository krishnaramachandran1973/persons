package com.cts;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cts.entities.AppUser;
import com.cts.entities.AppUserAuthority;
import com.cts.entities.Person;
import com.cts.repository.AppUserRepository;
import com.cts.repository.PersonRepository;
import com.cts.util.JwtUtil;

@SpringBootApplication
public class PersonsApplication {

	@Bean
	BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	JwtUtil jwtUtil() {
		return new JwtUtil();
	}

	public static void main(String[] args) {
		SpringApplication.run(PersonsApplication.class, args);
	}

	@Bean
	CommandLineRunner init(PersonRepository personRepository, AppUserRepository appUserRepository,
			BCryptPasswordEncoder encoder) {
		return args -> {
			List<Person> persons = Arrays.asList(Person.builder()
					.name("Krishna")
					.build(),
					Person.builder()
							.name("Rama")
							.build(),
					Person.builder()
							.name("Deepak")
							.build(),
					Person.builder()
							.name("Alice")
							.build(),
					Person.builder()
							.name("Ravi")
							.build(),
					Person.builder()
							.name("Rutuja")
							.build(),
					Person.builder()
							.name("Saloni")
							.build());
			personRepository.saveAll(persons);

			AppUser appUser = AppUser.builder()
					.username("admin")
					.password(encoder.encode("admin"))
					.authorities(Arrays.asList(AppUserAuthority.builder()
							.authority("ROLE_ADMIN")
							.build(),
							AppUserAuthority.builder()
									.authority("ROLE_USER")
									.build()))
					.build();
			appUserRepository.save(appUser);
			appUserRepository.findAll()
					.forEach(user -> System.out.println(user));
		};
	}

}
