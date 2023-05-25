package ru.gladun.historylearningplatform.mapstruct;

import org.mapstruct.Mapper;
import ru.gladun.historylearningplatform.dto.request.CommentDtoRequest;
import ru.gladun.historylearningplatform.dto.response.CommentDtoResponse;
import ru.gladun.historylearningplatform.entity.Comment;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = CommentMapStruct.class)
public interface CommentSetMapStruct {

    Set<Comment> toCommentList(Set<CommentDtoRequest> commentDtoRequests);

    Set<CommentDtoResponse> toCommentDtoResponseList(Set<Comment> comments);

}
