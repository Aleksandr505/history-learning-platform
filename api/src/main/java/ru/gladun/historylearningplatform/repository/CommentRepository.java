package ru.gladun.historylearningplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gladun.historylearningplatform.entity.Comment;

import java.util.List;
import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Set<Comment> findCommentsByArticleId(Long articleId);

}
