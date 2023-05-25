package ru.gladun.historylearningplatform.mapstruct;

import org.mapstruct.Mapper;
import ru.gladun.historylearningplatform.dto.response.RoleDtoResponse;
import ru.gladun.historylearningplatform.entity.Role;

import java.util.List;

@Mapper(componentModel = "spring", uses = RoleMapStruct.class)
public interface RoleListMapStruct {

    List<Role> toRoleList(List<RoleDtoResponse> roleDtoResponses);

    List<RoleDtoResponse> toRoleDtoList(List<Role> roles);

}
