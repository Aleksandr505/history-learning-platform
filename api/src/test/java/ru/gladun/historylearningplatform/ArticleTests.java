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
import ru.gladun.historylearningplatform.exception.ServerErrorCode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ArticleTests extends TestBase {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Test
    public void postArticleTest() throws Exception {
        UserDtoResponse user = createUser(new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com"));

        ArticleDtoRequest article = new ArticleDtoRequest("Modern history", "...some interesting content...",
                "2026-01-14", user.getId());
        mvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(article.getTitle()))
                .andExpect(jsonPath("$.content").value(article.getContent()))
                .andExpect(jsonPath("$.date").value(article.getDate()))
                .andExpect(jsonPath("$.userId").value(article.getUserId()));
    }

    @Test
    public void postArticleWithErrorsTest() throws Exception {
        ArticleDtoRequest article = new ArticleDtoRequest("", "...some interesting content...",
                "2026-01-14", null);
        mvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getArticlesTest() throws Exception {
        UserDtoResponse user = createUser(new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com"));

        createArticle(new ArticleDtoRequest("Modern history", "...some interesting content...",
                "2026-01-14", user.getId()));
        createArticle(new ArticleDtoRequest("Ancient history", "...some interesting content...",
                "1986-06-11", user.getId()));
        createArticle(new ArticleDtoRequest("Medieval history", "...some interesting content...",
                "2005-01-04", user.getId()));

        mvc.perform(get("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getArticleTest() throws Exception {
        UserDtoResponse user = createUser(new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com"));

        ArticleDtoResponse article = createArticle(new ArticleDtoRequest("Modern history", "...some interesting content...",
                "2026-01-14", user.getId()));

        mvc.perform(get("/api/articles/" + article.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void editArticleTest() throws Exception {
        UserDtoResponse user = createUser(new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com"));
        ArticleDtoResponse sourceArticle = createArticle(new ArticleDtoRequest("Modern history", "...some interesting content...",
                "2026-01-14", user.getId()));

        ArticleDtoRequest editedArticle = new ArticleDtoRequest("Modern history", "...not interesting content...",
                "2029-05-16", user.getId());
        mvc.perform(put("/api/articles/" + sourceArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editedArticle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(editedArticle.getTitle()))
                .andExpect(jsonPath("$.content").value(editedArticle.getContent()))
                .andExpect(jsonPath("$.date").value(editedArticle.getDate()))
                .andExpect(jsonPath("$.userId").value(editedArticle.getUserId()));
    }

    @Test
    public void deleteArticleTest() throws Exception {
        UserDtoResponse user = createUser(new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com"));

        ArticleDtoResponse article = createArticle(new ArticleDtoRequest("Modern history", "...some interesting content...",
                "2026-01-14", user.getId()));

        mvc.perform(delete("/api/articles/" + article.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteArticleWithErrorsTest() throws Exception {
        mvc.perform(delete("/api/articles/" + -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ServerErrorCode.ARTICLE_NOT_FOUND.getMessage()));
    }

}
