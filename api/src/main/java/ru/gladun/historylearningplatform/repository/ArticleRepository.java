package ru.gladun.historylearningplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gladun.historylearningplatform.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
