package io.github.dc.users.service;

import io.github.dc.users.domain.Gender;
import io.github.dc.users.domain.User;
import io.github.dc.users.repository.UserRepository;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest implements WithAssertions {
    private final UserRepository repository = mock(UserRepository.class);
    private final UserService userService = new UserService(repository);

    @Test
    void shouldValidateName() {
        User user = new User(1L, "", LocalDate.of(1996, Month.APRIL, 29), "France", "", Gender.MALE);
        var exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Name is mandatory and must not be France", exception.getMessage());

        user.setName("France");
        exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Name is mandatory and must not be France", exception.getMessage());
    }

    @Test
    void shouldValidateBirthdate() {
        User user = new User(1L, "John Doe", LocalDate.of(2010, Month.APRIL, 29), "France", "", Gender.MALE);
        var exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Invalid birthdate", exception.getMessage());
    }

    @Test
    void shouldValidateCountryOfResidence() {
        User user = new User(1L, "John Doe", LocalDate.of(1996, Month.APRIL, 29), "", "", Gender.MALE);
        var exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Invalid country of residence", exception.getMessage());

        user.setName("Germany");
        exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Invalid country of residence", exception.getMessage());
    }

    @Test
    void shouldValidatePhoneNumber() {
        User user = new User(1L, "John Doe", LocalDate.of(1996, Month.APRIL, 29), "France", "21105463", Gender.MALE);
        var exception = assertThrows(ValidationException.class, () -> userService.create(user));
        assertEquals("Invalid phone number", exception.getMessage());

    }

    @Test
    void shouldCreateUser() {
        User user = new User(1L, "John Doe", LocalDate.of(1996, Month.APRIL, 29), "France", "+33603357987", Gender.MALE);
        assertDoesNotThrow(() -> userService.create(user));
        verify(repository).save(user);
    }

}