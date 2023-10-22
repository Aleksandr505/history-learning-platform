package ru.gladun.historylearningplatform.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gladun.historylearningplatform.dto.request.UserDtoRequest;
import ru.gladun.historylearningplatform.dto.response.UserDtoResponse;
import ru.gladun.historylearningplatform.entity.User;
import ru.gladun.historylearningplatform.exception.ServerErrorCode;
import ru.gladun.historylearningplatform.exception.ServerException;
import ru.gladun.historylearningplatform.mapstruct.UserMapStruct;
import ru.gladun.historylearningplatform.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapStruct userMapStruct;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapStruct userMapStruct, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapStruct = userMapStruct;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDtoResponse register(UserDtoRequest userDtoRequest) throws ServerException {

        if (!userDtoRequest.getPassword().equals(userDtoRequest.getPasswordConfirm()))
            throw new ServerException(ServerErrorCode.WRONG_PASSWORD_CONFIRM);

        Optional<User> userDb = userRepository.findByUsername(userDtoRequest.getUsername());
        if (userDb.isPresent())
            throw new ServerException(ServerErrorCode.LOGIN_BUSY);

        userDtoRequest.setPassword(passwordEncoder.encode(userDtoRequest.getPassword()));
        User user = userMapStruct.toUser(userDtoRequest);

        userRepository.save(user);

        return userMapStruct.fromUserToUserDtoResponse(user);
    }
}
