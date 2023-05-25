package ru.gladun.historylearningplatform.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gladun.historylearningplatform.dto.response.RoleDtoResponse;
import ru.gladun.historylearningplatform.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapStruct {

    Role toRole(RoleDtoResponse roleDtoResponse);
    RoleDtoResponse fromRoleToRoleDtoResponse(Role role);

}
