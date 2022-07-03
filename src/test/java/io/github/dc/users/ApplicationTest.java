package io.github.dc.users;

import io.github.dc.users.domain.Gender;
import io.github.dc.users.domain.User;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Month;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest implements WithAssertions {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldGetUser() {
        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + port + "/users/42", User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldCreateUser() {
        User user = new User(null, "John Doe", LocalDate.of(1996, Month.APRIL, 29), "France", "+33603357987", Gender.MALE);
        ResponseEntity<User> response = this.restTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

}
