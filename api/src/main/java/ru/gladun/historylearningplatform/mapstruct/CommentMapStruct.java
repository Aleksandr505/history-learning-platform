package ru.gladun.historylearningplatform.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gladun.historylearningplatform.dto.request.CommentDtoRequest;
import ru.gladun.historylearningplatform.dto.response.ArticleDtoResponse;
import ru.gladun.historylearningplatform.dto.response.CommentDtoResponse;
import ru.gladun.historylearningplatform.entity.Article;
import ru.gladun.historylearningplatform.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapStruct {

    Comment toComment(CommentDtoRequest commentDtoRequest);

    @Mapping(source = "comment.user.id", target = "userId")
    @Mapping(source = "comment.article.id", target = "articleId")
    CommentDtoResponse fromCommentToCommentDtoResponse(Comment comment);

}
