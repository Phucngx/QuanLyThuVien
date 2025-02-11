package com.qltv.QLTV.Repository;

import com.qltv.QLTV.Entity.Articles;
import com.qltv.QLTV.Entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Articles, String> {
//    List<Comments> findAllCommentById(String articleId);
}
