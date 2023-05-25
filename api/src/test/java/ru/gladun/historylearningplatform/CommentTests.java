package ru.gladun.historylearningplatform;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gladun.historylearningplatform.dto.request.ArticleDtoRequest;
import ru.gladun.historylearningplatform.dto.request.CommentDtoRequest;
import ru.gladun.historylearningplatform.dto.request.UserDtoRequest;
import ru.gladun.historylearningplatform.dto.response.ArticleDtoResponse;
import ru.gladun.historylearningplatform.dto.response.CommentDtoResponse;
import ru.gladun.historylearningplatform.dto.response.UserDtoResponse;
import ru.gladun.historylearningplatform.exception.ServerErrorCode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CommentTests extends TestBase {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Test
    public void postCommentTest() throws Exception {
        UserDtoResponse user = createUser(new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com"));
        ArticleDtoResponse article = createArticle(new ArticleDtoRequest("Modern history", "...some interesting content...",
                "2026-01-14", user.getId()));

        CommentDtoRequest comment = new CommentDtoRequest("This article is very interesting!", "2026-02-23",
                user.getId(), article.getId());
        mvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(comment.getContent()))
                .andExpect(jsonPath("$.date").value(comment.getDate()))
                .andExpect(jsonPath("$.userId").value(comment.getUserId()))
                .andExpect(jsonPath("$.articleId").value(comment.getArticleId()));
    }

    @Test
    public void postCommentWithErrorsTest() throws Exception {
        UserDtoResponse user = createUser(new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com"));
        ArticleDtoResponse article = createArticle(new ArticleDtoRequest("Modern history", "...some interesting content...",
                "2026-01-14", user.getId()));

        CommentDtoRequest comment = new CommentDtoRequest("", "2062 23",
                user.getId(), article.getId());
        mvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getCommentTest() throws Exception {
        UserDtoResponse user = createUser(new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com"));
        ArticleDtoResponse article = createArticle(new ArticleDtoRequest("Modern history", "...some interesting content...",
                "2026-01-14", user.getId()));
        CommentDtoResponse comment = createComment(new CommentDtoRequest("This article is very interesting!", "2026-02-23",
                user.getId(), article.getId()));

        mvc.perform(get("/api/comments/" + comment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getCommentWithErrorsTest() throws Exception {
        mvc.perform(get("/api/comments/" + -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ServerErrorCode.COMMENT_NOT_FOUND.getMessage()));
    }

    @Test
    public void editCommentTest() throws Exception {
        UserDtoResponse user = createUser(new UserDtoRequest("Aleks17888", "12345678", "12345678",
                "Озацкий", "Александр", "Константинович", "aleks869@mail.com"));
        ArticleDtoResponse article = createArticle(new ArticleDtoRequest("Modern history", "...some interesting content...",
                "2026-01-14", user.getId()));
        CommentDtoResponse sourceComment = createComment(new CommentDtoRequest("This article is very interesting!", "2026-02-23",
                user.getId(), article.getId()));

        CommentDtoRequest editedComment = new CommentDtoRequest("This article is very boring!", "2027-03-18",
                user.getId(), article.getId());

        mvc.perform(put("/api/comments/" + sourceComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editedComment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(editedComment.getContent()))
                .andExpect(jsonPath("$.date").value(editedComment.getDate()))
                .andExpect(jsonPath("$.userId").value(editedComment.getUserId()))
                .andExpect(jsonPath("$.articleId").value(editedComment.getArticleId()));
    }

}
