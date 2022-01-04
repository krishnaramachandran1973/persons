package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	AppUser findByUsername(String username);

}
