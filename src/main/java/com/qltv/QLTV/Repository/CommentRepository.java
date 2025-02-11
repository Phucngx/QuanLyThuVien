package com.qltv.QLTV.Repository;

import com.qltv.QLTV.Entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, String> {
//    List<Comments> findByArticle_ArticleId(String articleId);

}
