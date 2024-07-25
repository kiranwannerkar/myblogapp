package com.myblog.service.impl;

import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.BlogAPIException;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.payload.CommentDTO;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import com.myblog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import javax.persistence.SecondaryTable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service   // service layer always return dto not entity
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    private ModelMapper modelMapper;

    public CommentServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDTO createCommnet(long postId, CommentDTO commentDTO) {
        Comment comment= mapToEntity(commentDTO);
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post","id",postId)
        );
        comment.setPost(post); // this way we can save the comment for this post
        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post","id",postId)
                 );
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDTO> dtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CommentDTO getCommentByid(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );
        if(comment.getPost().getId()!=post.getId()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belongs to post");
        }
        return mapToDto(comment); // here comment entity convert into commentDto
    }

    @Override
    public CommentDTO updateComment(long postId, long id, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", id)
        );
        if(comment.getPost().getId()!= post.getId()){
         throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Post not matching with comment");
        }
        comment.setId(id); //commentDTO.getId() here you have to give direct id bcz commentDTO doec not holding id or we r not passing id in json object
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());
        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);
    }

    @Override
    public void deleteComment(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", id)
        );
        if(comment.getPost().getId()!= post.getId()){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Post not matching with comment");
        }
        commentRepository.deleteById(id);
    }

    private CommentDTO mapToDto(Comment newComment) {
        CommentDTO dto = modelMapper.map(newComment, CommentDTO.class);
//        CommentDTO dto=new CommentDTO();
//        dto.setId(newComment.getId());
//        dto.setName(newComment.getName());
//        dto.setEmail(newComment.getEmail());
//        dto.setBody(newComment.getBody());
//        dto.setPost(newComment.getPost());
        return dto;
    }

    private Comment mapToEntity(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
//        Comment comment=new Comment();
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());
        return comment;
    }
}

