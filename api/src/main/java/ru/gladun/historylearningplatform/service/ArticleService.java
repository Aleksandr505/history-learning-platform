package ru.gladun.historylearningplatform.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gladun.historylearningplatform.dto.request.ArticleDtoRequest;
import ru.gladun.historylearningplatform.dto.response.ArticleDtoResponse;
import ru.gladun.historylearningplatform.dto.response.CommentDtoResponse;
import ru.gladun.historylearningplatform.entity.Article;
import ru.gladun.historylearningplatform.entity.Comment;
import ru.gladun.historylearningplatform.entity.User;
import ru.gladun.historylearningplatform.exception.ServerErrorCode;
import ru.gladun.historylearningplatform.exception.ServerException;
import ru.gladun.historylearningplatform.mapstruct.ArticleListMapStruct;
import ru.gladun.historylearningplatform.mapstruct.ArticleMapStruct;
import ru.gladun.historylearningplatform.mapstruct.CommentMapStruct;
import ru.gladun.historylearningplatform.repository.ArticleRepository;
import ru.gladun.historylearningplatform.repository.CommentRepository;
import ru.gladun.historylearningplatform.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ArticleMapStruct articleMapStruct;
    private final ArticleListMapStruct articleListMapStruct;
    private final CommentMapStruct commentMapStruct;

    public ArticleDtoResponse postArticle(ArticleDtoRequest articleDtoRequest) {
        User author = userRepository.findById(articleDtoRequest.getUserId())
                .orElseThrow(() -> new ServerException(ServerErrorCode.USER_NOT_FOUND));

        Article article = articleMapStruct.toArticle(articleDtoRequest);
        article.setUser(author);

        articleRepository.save(article);

        log.info("postArticle: " + article);
        return articleMapStruct.fromArticleToArticleDtoResponse(article);
    }

    @Transactional
    public List<ArticleDtoResponse> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleDtoResponse> articleDtoResponses = new ArrayList<>();
        for (Article article : articles) {
            Set<Comment> comments = commentRepository.findCommentsByArticleId(article.getId());
            article.setComments(comments);
            articleDtoResponses.add(articleMapStruct.fromArticleToArticleDtoResponse(article));
        }

        log.info("getAllArticles: " + articleDtoResponses);
        return articleDtoResponses;
    }

    public ArticleDtoResponse getArticle(long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ServerException(ServerErrorCode.ARTICLE_NOT_FOUND));
        Set<Comment> comments = commentRepository.findCommentsByArticleId(id);
        article.setComments(comments);

        log.info("getArticle: " + article);
        return articleMapStruct.fromArticleToArticleDtoResponse(article);
    }

    public ArticleDtoResponse editArticle(long id, ArticleDtoRequest articleDtoRequest) {
        Article articleDb = articleRepository.findById(id)
                .orElseThrow(() -> new ServerException(ServerErrorCode.ARTICLE_NOT_FOUND));

        Article article = articleMapStruct.toArticle(articleDtoRequest);
        article.setId(id);
        article.setUser(articleDb.getUser());
        article.setComments(articleDb.getComments());

        articleRepository.save(article);

        log.info("editArticle: " + article);
        return articleMapStruct.fromArticleToArticleDtoResponse(article);
    }

    public void deleteArticle(long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ServerException(ServerErrorCode.ARTICLE_NOT_FOUND));

        log.info("deleteArticle: " + article);
        articleRepository.delete(article);
    }

}
