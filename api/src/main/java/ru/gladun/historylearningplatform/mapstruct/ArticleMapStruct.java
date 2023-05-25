package ru.gladun.historylearningplatform.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gladun.historylearningplatform.dto.request.ArticleDtoRequest;
import ru.gladun.historylearningplatform.dto.response.ArticleDtoResponse;
import ru.gladun.historylearningplatform.entity.Article;

@Mapper(componentModel = "spring", uses = CommentSetMapStruct.class)
public interface ArticleMapStruct {

    Article toArticle(ArticleDtoRequest articleDtoRequest);

    @Mapping(source = "article.user.id", target = "userId")
    ArticleDtoResponse fromArticleToArticleDtoResponse(Article article);

}
