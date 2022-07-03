package io.github.dc.users.repository;

import io.github.dc.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNameAndBirthdate(String name, LocalDate birthdate);
}
