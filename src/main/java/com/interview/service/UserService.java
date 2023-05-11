package com.interview.service;

import com.interview.exception.GithubException;
import com.interview.model.dto.UserDto;
import com.interview.model.entity.UserInformation;
import com.interview.remote.client.github.GithubApiClient;
import com.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GithubApiClient githubApiClient;

    @Transactional
    public UserDto getUserInformation(String login) {
        var githubUser = githubApiClient.getUserDetails(login);
        if (ObjectUtils.isEmpty(githubUser.getBody())) {
            throw new GithubException("Something went wrong. Please try again later.");
        }
        processGithubUser(login);
        return UserDto.from(githubUser.getBody());
    }

    private void processGithubUser(String login) {
        var user = userRepository.findById(login)
                .orElse(UserInformation.builder()
                        .login(login)
                        .requestCount(0)
                        .build());
        user.addRequestCount();
        userRepository.save(user);
    }

}
