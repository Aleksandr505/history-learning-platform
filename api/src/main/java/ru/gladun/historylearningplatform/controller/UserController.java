package ru.gladun.historylearningplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gladun.historylearningplatform.dto.request.UserDtoRequest;
import ru.gladun.historylearningplatform.dto.response.UserDtoResponse;
import ru.gladun.historylearningplatform.exception.ServerException;
import ru.gladun.historylearningplatform.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8888")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse register(@Valid @RequestBody UserDtoRequest registerUserDtoRequest) throws ServerException {
        return userService.register(registerUserDtoRequest);
    }

}
