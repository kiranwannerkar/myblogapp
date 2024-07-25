package com.myblog.controller;

import com.myblog.entity.Post;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController { // PostController -> going to return api

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")  // this mean user with role admin can access this
    @RequestMapping
   public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){ // to get respose status code u have to use ResponseEntity<PostDto>
       if(bindingResult.hasErrors()){
           return new ResponseEntity<Object>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);// this is the way we can return the response back,, to resolve error change the PostDTO class into Object class
       // when it crashes this above  return response u will get
       }

        PostDto  dto = postService.createPost(postDto);
       return new ResponseEntity<>(dto, HttpStatus.CREATED) ;
       // if doesnt crashesh then u will get aboev response
    }
    //http://localhost:8080/api/posts?pageNo=3&pageSize=3&sortBy=title&sortDir=asc
    @GetMapping
    public PostResponse getAllPost(@RequestParam(value = "pageNo",defaultValue ="0",required = false) int pageNo,
                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false /* if we dont provide pageSize it will automatically take default*/)int pageSize,
                                   @RequestParam(value = "sortBy",defaultValue = "id",required = false)String sortBy,
                                   @RequestParam(value = "sortDir",defaultValue = "asc")String sortDir){  //required = false means not required or mandetory
                return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        PostDto dto = postService.getPostById(id);
        return ResponseEntity.ok(dto); //it automatically return status 200 with dto object
        //OR  new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")  // this mean user with role admin can access this
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable long id){
        PostDto postDto1 = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // this mean user with role admin can access this
    public ResponseEntity<String> deletePostByid(@PathVariable("id")long id){
        postService.deletePostByid(id);
        return new ResponseEntity<>("Post entity deleted!!",HttpStatus.OK);
    }
}
