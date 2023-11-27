package ru.gladun.historylearningplatform.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gladun.historylearningplatform.dto.request.ArticleDtoRequest;
import ru.gladun.historylearningplatform.dto.request.CommentDtoRequest;
import ru.gladun.historylearningplatform.dto.response.ArticleDtoResponse;
import ru.gladun.historylearningplatform.dto.response.CommentDtoResponse;
import ru.gladun.historylearningplatform.entity.Article;
import ru.gladun.historylearningplatform.entity.Comment;
import ru.gladun.historylearningplatform.entity.User;
import ru.gladun.historylearningplatform.exception.ServerErrorCode;
import ru.gladun.historylearningplatform.exception.ServerException;
import ru.gladun.historylearningplatform.mapstruct.CommentMapStruct;
import ru.gladun.historylearningplatform.repository.ArticleRepository;
import ru.gladun.historylearningplatform.repository.CommentRepository;
import ru.gladun.historylearningplatform.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentMapStruct commentMapStruct;

    public CommentDtoResponse postComment(CommentDtoRequest commentDtoRequest) {
        User author = userRepository.findById(commentDtoRequest.getUserId())
                .orElseThrow(() -> new ServerException(ServerErrorCode.USER_NOT_FOUND));

        Article article = articleRepository.findById(commentDtoRequest.getArticleId())
                .orElseThrow(() -> new ServerException(ServerErrorCode.ARTICLE_NOT_FOUND));

        Comment comment = commentMapStruct.toComment(commentDtoRequest);
        comment.setUser(author);
        comment.setArticle(article);

        commentRepository.save(comment);

        log.info("postComment: " + comment);
        return commentMapStruct.fromCommentToCommentDtoResponse(comment);
    }

    public CommentDtoResponse getComment(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ServerException(ServerErrorCode.COMMENT_NOT_FOUND));

        log.info("getComment: " + comment);
        return commentMapStruct.fromCommentToCommentDtoResponse(comment);
    }

    public CommentDtoResponse editComment(long id, CommentDtoRequest commentDtoRequest) {
        Comment commentDb = commentRepository.findById(id)
                .orElseThrow(() -> new ServerException(ServerErrorCode.COMMENT_NOT_FOUND));

        Comment comment = commentMapStruct.toComment(commentDtoRequest);
        comment.setId(id);
        comment.setUser(commentDb.getUser());
        comment.setArticle(commentDb.getArticle());

        commentRepository.save(comment);

        log.info("editComment: " + comment);
        return commentMapStruct.fromCommentToCommentDtoResponse(comment);
    }

}
