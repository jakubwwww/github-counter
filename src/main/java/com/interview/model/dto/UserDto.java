package com.interview.model.dto;

import com.interview.remote.model.GithubUserDetailsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Builder
@RequiredArgsConstructor
public class UserDto {

    private final Long id;
    private final String login;
    private final String name;
    private final String type;
    private final String avatarUrl;
    private final String createdAt;
    private final String calculations;

    public static UserDto from(GithubUserDetailsDto githubUserDetailsDto) {
        return UserDto.builder()
                .id(githubUserDetailsDto.getId())
                .login(githubUserDetailsDto.getLogin())
                .name(githubUserDetailsDto.getName())
                .type(githubUserDetailsDto.getType())
                .avatarUrl(githubUserDetailsDto.getAvatarUrl())
                .createdAt(githubUserDetailsDto.getCreatedAt().toString())
                .calculations(calculate(githubUserDetailsDto))
                .build();
    }

    private static String calculate(GithubUserDetailsDto githubUserDetailsDto) {
        if (githubUserDetailsDto.getFollowers() <= 0) {
            return "undefined";
        }
        var denominator = BigDecimal.valueOf(githubUserDetailsDto.getFollowers() * (2 + githubUserDetailsDto.getNumberOfPublicRepos()));
        return BigDecimal.valueOf(6).divide(denominator, 3, RoundingMode.DOWN).toString();
    }

}
