package ru.gladun.historylearningplatform.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gladun.historylearningplatform.dto.response.ArticleDtoResponse;
import ru.gladun.historylearningplatform.dto.response.RoleDtoResponse;
import ru.gladun.historylearningplatform.dto.response.UserDtoResponse;
import ru.gladun.historylearningplatform.entity.Article;
import ru.gladun.historylearningplatform.entity.Comment;
import ru.gladun.historylearningplatform.entity.Role;
import ru.gladun.historylearningplatform.entity.User;
import ru.gladun.historylearningplatform.exception.ServerErrorCode;
import ru.gladun.historylearningplatform.exception.ServerException;
import ru.gladun.historylearningplatform.mapstruct.RoleMapStruct;
import ru.gladun.historylearningplatform.mapstruct.UserMapStruct;
import ru.gladun.historylearningplatform.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final UserMapStruct userMapStruct;

    public AdminService(UserRepository userRepository, UserMapStruct userMapStruct) {
        this.userRepository = userRepository;
        this.userMapStruct = userMapStruct;
    }

    @Transactional
    public List<UserDtoResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDtoResponse> userDtoResponses = new ArrayList<>();
        for (User user : users) {
            userDtoResponses.add(userMapStruct.fromUserToUserDtoResponse(user));
        }
        return userDtoResponses;
    }

    public UserDtoResponse getUser(long id) throws ServerException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServerException(ServerErrorCode.USER_NOT_FOUND));

        return userMapStruct.fromUserToUserDtoResponse(user);
    }

}
