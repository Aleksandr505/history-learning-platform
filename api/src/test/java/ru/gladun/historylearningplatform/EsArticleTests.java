package ru.gladun.historylearningplatform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gladun.historylearningplatform.dto.request.ArticleDtoRequest;
import ru.gladun.historylearningplatform.dto.request.UserDtoRequest;
import ru.gladun.historylearningplatform.dto.response.ArticleDtoResponse;
import ru.gladun.historylearningplatform.dto.response.UserDtoResponse;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EsArticleTests extends TestBase {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Test
    public void searchArticleWithIndexTest() throws Exception {
        UserDtoResponse user = createUser(new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com"));
        ArticleDtoResponse article1 = createArticle(new ArticleDtoRequest("Ancient history", "...some interesting content...",
                "2026-01-14", user.getId()));
        ArticleDtoResponse article2 = createArticle(new ArticleDtoRequest("Modern history", "...some interesting content...",
                "2026-01-14", user.getId()));
        ArticleDtoResponse article3 = createArticle(new ArticleDtoRequest("Post-Modern history", "...some interesting content...",
                "2026-01-14", user.getId()));

        mvc.perform(get("/api/articles/search?query=Modern"))
                .andExpect(status().isOk());
    }

}
