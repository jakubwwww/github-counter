package com.interview.remote.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = GithubUserDetailsDto.GithubUserDetailsDtoBuilder.class)
public class GithubUserDetailsDto {

    private final String login;
    private final long id;
    @JsonProperty("avatar_url")
    private final String avatarUrl;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;
    private final String name;
    private final String type;
    private final long followers;
    @JsonProperty("public_repos")
    private final long numberOfPublicRepos;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GithubUserDetailsDtoBuilder {

    }

}
