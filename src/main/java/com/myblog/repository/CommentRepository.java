package com.myblog.repository;

import com.myblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>{

    List<Comment> findByPostId(long postId); // spring boot will automatically understand that I have to find by email
   // above method only for comment table it will go to the comment table , and based on postId we will get all the list of comment
}
