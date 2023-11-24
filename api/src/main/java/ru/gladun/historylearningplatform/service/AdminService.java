package ru.gladun.historylearningplatform.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gladun.historylearningplatform.dto.response.UserDtoResponse;
import ru.gladun.historylearningplatform.entity.User;
import ru.gladun.historylearningplatform.exception.ServerErrorCode;
import ru.gladun.historylearningplatform.exception.ServerException;
import ru.gladun.historylearningplatform.mapstruct.UserMapStruct;
import ru.gladun.historylearningplatform.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final UserMapStruct userMapStruct;

    @Transactional
    public List<UserDtoResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDtoResponse> userDtoResponses = new ArrayList<>();
        for (User user : users) {
            userDtoResponses.add(userMapStruct.fromUserToUserDtoResponse(user));
        }
        log.info("getAllUsers: " + userDtoResponses);
        return userDtoResponses;
    }

    public UserDtoResponse getUser(long id) throws ServerException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServerException(ServerErrorCode.USER_NOT_FOUND));

        log.info("getUser: " + user.toString());
        return userMapStruct.fromUserToUserDtoResponse(user);
    }

}
