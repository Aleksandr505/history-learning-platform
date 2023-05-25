package ru.gladun.historylearningplatform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.gladun.historylearningplatform.dto.request.ArticleDtoRequest;
import ru.gladun.historylearningplatform.dto.request.CommentDtoRequest;
import ru.gladun.historylearningplatform.dto.request.UserDtoRequest;
import ru.gladun.historylearningplatform.dto.response.ArticleDtoResponse;
import ru.gladun.historylearningplatform.dto.response.CommentDtoResponse;
import ru.gladun.historylearningplatform.dto.response.UserDtoResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/sql/schema.sql")
public class TestBase {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    protected UserDtoResponse createUser(UserDtoRequest user) throws Exception {
        ResultActions resultActions = mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.patronymic").value(user.getPatronymic()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), UserDtoResponse.class);
    }

    protected ArticleDtoResponse createArticle(ArticleDtoRequest article) throws Exception {
        ResultActions resultActions = mvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(article.getTitle()))
                .andExpect(jsonPath("$.content").value(article.getContent()))
                .andExpect(jsonPath("$.date").value(article.getDate()))
                .andExpect(jsonPath("$.userId").value(article.getUserId()));
        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), ArticleDtoResponse.class);
    }

    protected CommentDtoResponse createComment(CommentDtoRequest comment) throws Exception {
        ResultActions resultActions = mvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(comment.getContent()))
                .andExpect(jsonPath("$.date").value(comment.getDate()))
                .andExpect(jsonPath("$.userId").value(comment.getUserId()))
                .andExpect(jsonPath("$.articleId").value(comment.getArticleId()));
        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), CommentDtoResponse.class);
    }

}
