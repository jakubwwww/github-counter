# GitHub Counter
This Java application provides an API to retrieve user information from GitHub and counts the number of requests for a specific user.

### Technologies Used

    Java 17
    Spring Boot 3
    Maven
    Feign

### Running the Application

To run the application, clone the repository and run the following command:

    mvn spring-boot:run

By default, the application runs on port 8080. You can access the API at 

    http://localhost:8080/github-counter/users/{login}

### Swagger-ui
   
    http://localhost:8080/github-counter/swagger-ui/index.html

### h2-console

    http://localhost:8080/github-counter/h2-console 
credentials: user/1234


### Docker
Make sure that you are in github-proxy catalog (at the same level as the dockerfile)
1. docker build --tag=github-counter:latest . or add a tag you like
2. docker run -p8887:8080 github-counter:latest 

then you can call api 

    localhost:8887/github-counter/users/{login}
    