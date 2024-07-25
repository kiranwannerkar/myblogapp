package com.myblog.controller;

import com.myblog.entity.Comment;
import com.myblog.payload.CommentDTO;
import com.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<Object> createComment(@PathVariable long id, @Valid @RequestBody CommentDTO commentDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR); // here multiple object getting return so used generic class that is object
        }
        CommentDTO dto = commentService.createCommnet(id, commentDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable long postId){
        return commentService.getCommentsByPostId(postId); // it will return list of comments
    }
    //http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long postId,@PathVariable("id") long commentId){
        CommentDTO dto = commentService.getCommentByid(postId, commentId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/1/comments/1
 @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(
         @PathVariable long postId,
         @PathVariable("id") long id,
         @RequestBody CommentDTO commentDTO
 ){
      CommentDTO dto=commentService.updateComment(postId,id,commentDTO) ;
      return new ResponseEntity<>(dto,HttpStatus.OK);
 }

 //http://localhost:8080/api/posts/1/comments/1
 @DeleteMapping("/posts/{postId}/comments/{id}")
 public ResponseEntity<String> deleteComment(
         @PathVariable long postId,
         @PathVariable("id") long id
 ){
        commentService.deleteComment(postId,id);
        return new ResponseEntity<>("Comment is deleted",HttpStatus.OK);

    }

}
