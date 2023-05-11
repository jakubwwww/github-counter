package com.interview.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.interview.exception.GithubException;
import com.interview.model.entity.UserInformation;
import com.interview.remote.client.github.GithubApiClient;
import com.interview.remote.model.GithubUserDetailsDto;
import com.interview.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    GithubApiClient githubApiClient;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    @Test
    void getUserInformation_CorrectDataAndUserExists_UserReturned() throws IOException {
        var githubUser = objectMapper.readValue(Files.readString(Paths.get("src/test/resources/github_user.json")), GithubUserDetailsDto.class);
        String login = "abcd";
        UserInformation userWithTwoRequests = createUserInformation(2);
        when(githubApiClient.getUserDetails(login)).thenReturn(ResponseEntity.ok(githubUser));
        when(userRepository.findById(eq(login))).thenReturn(Optional.of(createUserInformation(1)));
        when(userRepository.save(userWithTwoRequests)).thenReturn(userWithTwoRequests);

        var result = userService.getUserInformation(login);

        Assertions.assertEquals("abcd test", result.getName());
        Assertions.assertEquals("undefined", result.getCalculations());
        Assertions.assertEquals(9999, result.getId());
    }

    @Test
    void getUserInformation_CorrectDataAndUserDoesNotExist_UserReturned() throws IOException {
        var githubUser = objectMapper.readValue(Files.readString(Paths.get("src/test/resources/github_user.json")), GithubUserDetailsDto.class);
        String login = "abcd";
        UserInformation userWithOneRequest = createUserInformation(1);
        when(githubApiClient.getUserDetails(login)).thenReturn(ResponseEntity.ok(githubUser));
        when(userRepository.findById(eq(login))).thenReturn(Optional.empty());
        when(userRepository.save(userWithOneRequest)).thenReturn(userWithOneRequest);

        var result = userService.getUserInformation(login);

        Assertions.assertEquals("abcd test", result.getName());
        Assertions.assertEquals("undefined", result.getCalculations());
        Assertions.assertEquals(9999L, result.getId());
    }

    @Test
    void getUserInformation_EmptyGithubResponseBody_GithubExceptionThrown() {
        String login = "abcd";
        when(githubApiClient.getUserDetails(eq(login))).thenReturn(ResponseEntity.internalServerError().body(null));

        var result = Assertions.assertThrows(GithubException.class, () -> userService.getUserInformation(login));

        Assertions.assertEquals("Something went wrong. Please try again later.", result.getMessage());
    }

    private UserInformation createUserInformation(int numberOfRequests) {
        return UserInformation.builder()
                .requestCount(numberOfRequests)
                .login("abcd")
                .build();
    }
}
