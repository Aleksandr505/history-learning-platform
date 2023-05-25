package ru.gladun.historylearningplatform.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.gladun.historylearningplatform.document.Article;

import java.util.List;

public interface EsArticleRepository extends ElasticsearchRepository<Article, Long> {
    List<Article> findByTitle(String title);
}
