package ru.gladun.historylearningplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gladun.historylearningplatform.entity.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {

    private Long id;

    private String username;

    private String password;

    private String lastName;

    private String firstName;

    private String patronymic;

    private String email;

    private Set<RoleDtoResponse> roles;

}
