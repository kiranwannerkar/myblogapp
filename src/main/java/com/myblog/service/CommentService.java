package com.myblog.service;

import com.myblog.entity.Comment;
import com.myblog.payload.CommentDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
   CommentDTO createCommnet(long postId, CommentDTO commentDTO);
   List<CommentDTO> getCommentsByPostId(long postId);

  CommentDTO getCommentByid(long postId,long commentId);

   CommentDTO updateComment(long postId, long id, CommentDTO commentDTO);

    void deleteComment(long postId, long id);
}
