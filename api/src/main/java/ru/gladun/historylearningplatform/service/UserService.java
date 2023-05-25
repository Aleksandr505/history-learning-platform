package ru.gladun.historylearningplatform.service;

import org.springframework.stereotype.Service;
import ru.gladun.historylearningplatform.dto.request.UserDtoRequest;
import ru.gladun.historylearningplatform.dto.response.RoleDtoResponse;
import ru.gladun.historylearningplatform.dto.response.UserDtoResponse;
import ru.gladun.historylearningplatform.entity.Role;
import ru.gladun.historylearningplatform.entity.User;
import ru.gladun.historylearningplatform.exception.ServerErrorCode;
import ru.gladun.historylearningplatform.exception.ServerException;
import ru.gladun.historylearningplatform.mapstruct.RoleMapStruct;
import ru.gladun.historylearningplatform.mapstruct.UserMapStruct;
import ru.gladun.historylearningplatform.repository.RoleRepository;
import ru.gladun.historylearningplatform.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapStruct userMapStruct;
    private final RoleMapStruct roleMapStruct;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapStruct userMapStruct, RoleMapStruct roleMapStruct) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapStruct = userMapStruct;
        this.roleMapStruct = roleMapStruct;
    }

    public UserDtoResponse register(UserDtoRequest userDtoRequest) throws ServerException {

        if (!userDtoRequest.getPassword().equals(userDtoRequest.getPasswordConfirm()))
            throw new ServerException(ServerErrorCode.WRONG_PASSWORD_CONFIRM);

        User userDb = userRepository.findByUsername(userDtoRequest.getUsername());
        if (userDb != null)
            throw new ServerException(ServerErrorCode.LOGIN_BUSY);

        User user = userMapStruct.toUser(userDtoRequest);
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));

        userRepository.save(user);

        return userMapStruct.fromUserToUserDtoResponse(user);
    }
}
