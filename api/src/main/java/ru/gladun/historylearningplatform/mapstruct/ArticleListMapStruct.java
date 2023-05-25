package ru.gladun.historylearningplatform.mapstruct;

import org.mapstruct.Mapper;
import ru.gladun.historylearningplatform.dto.request.ArticleDtoRequest;
import ru.gladun.historylearningplatform.dto.response.ArticleDtoResponse;
import ru.gladun.historylearningplatform.entity.Article;

import java.util.List;

@Mapper(componentModel = "spring", uses = ArticleMapStruct.class)
public interface ArticleListMapStruct {

    List<Article> toArticleList(List<ArticleDtoRequest> articleDtoRequests);

    List<ArticleDtoResponse> toArticleDtoResponseList(List<Article> articles);

}
