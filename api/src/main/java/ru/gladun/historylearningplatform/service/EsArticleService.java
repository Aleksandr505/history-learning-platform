package ru.gladun.historylearningplatform.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;
import ru.gladun.historylearningplatform.document.Article;
import ru.gladun.historylearningplatform.mapstruct.ArticleMapStruct;
import ru.gladun.historylearningplatform.repository.ArticleRepository;
import ru.gladun.historylearningplatform.repository.EsArticleRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EsArticleService {

    private final RestHighLevelClient esClient;

    private final EsArticleRepository esArticleRepository;

    public void indexArticle(Long id, String title, String date) throws Exception {
        Article document = new Article(id, title, date);
        esArticleRepository.save(document);

        log.info("indexArticle: " + document);
    }

    public List<Long> searchArticles(String searchString) throws Exception {
       List<Article> documents = esArticleRepository.findByTitle(searchString);

        List<Long> idList = new ArrayList<>();
        for (Article document : documents) {
            idList.add(document.getId());
        }

        log.info("searchArticles: " + idList);
        return idList;
    }

    public void delete(Long id) throws IOException {
        esArticleRepository.deleteById(id);
    }

}
