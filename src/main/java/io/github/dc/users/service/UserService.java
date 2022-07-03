package io.github.dc.users.service;

import io.github.dc.users.domain.User;
import io.github.dc.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final String FRANCE = "france";
    private static final String PHONE_NUMBER_REGEX = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$";
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        validate(user);
        return userRepository.save(user);
    }

    private void validate(User user) {
        validateName(user.getName());
        validateCountryOfResidence(user.getCountryOfResidence());
        validateBirthdate(user.getBirthdate());
        validatePhoneNumber(user.getPhoneNumber());
        checkIfAlreadyExists(user);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank() || FRANCE.equalsIgnoreCase(name)) {
            throw new ValidationException("Name is mandatory and must not be France");
        }
    }

    private void validateBirthdate(LocalDate birthdate) {
        if (birthdate == null || LocalDate.now().minusYears(18).isBefore(birthdate)) {
            throw new ValidationException("Invalid birthdate");
        }
    }

    private void validateCountryOfResidence(String countryOfResidence) {
        if (countryOfResidence == null || countryOfResidence.isBlank() || !FRANCE.equalsIgnoreCase(countryOfResidence)) {
            throw new ValidationException("Invalid country of residence");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !Pattern.matches(PHONE_NUMBER_REGEX, phoneNumber)) {
            throw new ValidationException("Invalid phone number");
        }
    }

    private void checkIfAlreadyExists(User user) {
        if (userRepository.existsByNameAndBirthdate(user.getName(), user.getBirthdate())) {
            throw new UserAlreadyExistsException();
        }
    }

    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }
}

