package ru.gladun.historylearningplatform.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gladun.historylearningplatform.dto.request.ArticleDtoRequest;
import ru.gladun.historylearningplatform.dto.response.ArticleDtoResponse;
import ru.gladun.historylearningplatform.exception.ServerException;
import ru.gladun.historylearningplatform.service.ArticleService;
import ru.gladun.historylearningplatform.service.EsArticleService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8888")
@RequestMapping("/api")
public class ArticleController {

    private final ArticleService articleService;

    private final EsArticleService esArticleService;

    public ArticleController(ArticleService articleService, EsArticleService esArticleService) {
        this.articleService = articleService;
        this.esArticleService = esArticleService;
    }

    @PostMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ArticleDtoResponse postArticle(@Valid @RequestBody ArticleDtoRequest articleDtoRequest) throws Exception {
        ArticleDtoResponse response = articleService.postArticle(articleDtoRequest);
        esArticleService.indexArticle(response.getId(), response.getTitle(), response.getDate());
        return response;
    }

    @GetMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArticleDtoResponse> getArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping(value = "/articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArticleDtoResponse getArticle(@PathVariable long id) throws ServerException {
        return articleService.getArticle(id);
    }

    @PutMapping(value = "/articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ArticleDtoResponse editArticle(@PathVariable long id, @Valid @RequestBody ArticleDtoRequest articleDtoRequest) throws Exception {
        ArticleDtoResponse response = articleService.editArticle(id, articleDtoRequest);
        esArticleService.indexArticle(response.getId(), response.getTitle(), response.getDate());
        return response;
    }

    @DeleteMapping(value = "/articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteArticle(@PathVariable long id) throws ServerException, IOException {
        articleService.deleteArticle(id);
        esArticleService.delete(id);
    }

    @GetMapping("/articles/search")
    public List<ArticleDtoResponse> searchArticles(@RequestParam("query") String query) throws Exception {
        List<ArticleDtoResponse> response = new ArrayList<>();
        List<Long> idList = esArticleService.searchArticles(query);
        for (Long id : idList) {
            ArticleDtoResponse article = articleService.getArticle(id);
            response.add(article);
        }
        return response;
    }

}
