package com.interview.remote.client.github;

import com.interview.remote.model.GithubUserDetailsDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "githubApiClient", url = "https://api.github.com")
@Headers("Accept: application/vnd.github+json")
public interface GithubApiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GithubUserDetailsDto> getUserDetails(@PathVariable String username);
}
