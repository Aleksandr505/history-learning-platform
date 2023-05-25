package ru.gladun.historylearningplatform.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gladun.historylearningplatform.dto.request.UserDtoRequest;
import ru.gladun.historylearningplatform.dto.response.UserDtoResponse;
import ru.gladun.historylearningplatform.entity.User;

@Mapper(componentModel = "spring", uses = RoleListMapStruct.class)
public interface UserMapStruct {

    User toUser(UserDtoRequest userDtoRequest);

    UserDtoResponse fromUserToUserDtoResponse(User user);

}
