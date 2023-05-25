package ru.gladun.historylearningplatform;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gladun.historylearningplatform.dto.request.UserDtoRequest;
import ru.gladun.historylearningplatform.exception.ServerErrorCode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserTests extends TestBase {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Test
    public void registerUserTest() throws Exception {
        UserDtoRequest user = new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com");
        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.patronymic").value(user.getPatronymic()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void registerUserWithErrorsTest() throws Exception {
        UserDtoRequest user = new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "", "", "Константинович", "aleks869@mail.com");
        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerUserWithBusyUsernameTest() throws Exception {
        createUser(new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com"));

        UserDtoRequest user = new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com");
        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ServerErrorCode.LOGIN_BUSY.getMessage()));
    }

    @Test
    public void getUsersTest() throws Exception {
        createUser(new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com"));
        createUser(new UserDtoRequest("Vladislav48", "12345678", "12345678",
                "Karpov", "Vlad", "Vladislavovich", "vlad425@gmail.com"));
        createUser(new UserDtoRequest("Oleg142", "12345678", "12345678",
                "Kolesnikov", "Oleg", "Anatolyevich", "oleg2365@gmail.com"));

        mvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
