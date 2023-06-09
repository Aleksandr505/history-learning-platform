package ru.gladun.historylearningplatform.service;

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
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ArticleMapStruct articleMapStruct;
    private final ArticleListMapStruct articleListMapStruct;
    private final CommentMapStruct commentMapStruct;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, CommentRepository commentRepository, ArticleMapStruct articleMapStruct, ArticleListMapStruct articleListMapStruct, CommentMapStruct commentMapStruct) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.articleMapStruct = articleMapStruct;
        this.articleListMapStruct = articleListMapStruct;
        this.commentMapStruct = commentMapStruct;
    }

    public ArticleDtoResponse postArticle(ArticleDtoRequest articleDtoRequest) throws ServerException {
        User author = userRepository.findById(articleDtoRequest.getUserId())
                .orElseThrow(() -> new ServerException(ServerErrorCode.USER_NOT_FOUND));

        Article article = articleMapStruct.toArticle(articleDtoRequest);
        article.setUser(author);

        articleRepository.save(article);

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
        return articleDtoResponses;
    }

    public ArticleDtoResponse getArticle(long id) throws ServerException {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ServerException(ServerErrorCode.ARTICLE_NOT_FOUND));
        Set<Comment> comments = commentRepository.findCommentsByArticleId(id);
        article.setComments(comments);

        return articleMapStruct.fromArticleToArticleDtoResponse(article);
    }

    public ArticleDtoResponse editArticle(long id, ArticleDtoRequest articleDtoRequest) throws ServerException {
        Article articleDb = articleRepository.findById(id)
                .orElseThrow(() -> new ServerException(ServerErrorCode.ARTICLE_NOT_FOUND));

        Article article = articleMapStruct.toArticle(articleDtoRequest);
        article.setId(id);
        article.setUser(articleDb.getUser());
        article.setComments(articleDb.getComments());

        articleRepository.save(article);

        return articleMapStruct.fromArticleToArticleDtoResponse(article);
    }

    public void deleteArticle(long id) throws ServerException {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ServerException(ServerErrorCode.ARTICLE_NOT_FOUND));

        articleRepository.delete(article);
    }

}
