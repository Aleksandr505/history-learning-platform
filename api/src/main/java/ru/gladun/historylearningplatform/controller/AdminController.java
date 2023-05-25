package ru.gladun.historylearningplatform.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gladun.historylearningplatform.dto.response.ArticleDtoResponse;
import ru.gladun.historylearningplatform.dto.response.UserDtoResponse;
import ru.gladun.historylearningplatform.exception.ServerException;
import ru.gladun.historylearningplatform.service.AdminService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8888")
@RequestMapping("/api")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDtoResponse> getUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse getUser(@PathVariable long id) throws ServerException {
        return adminService.getUser(id);
    }

}
