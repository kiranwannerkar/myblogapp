package com.myblog.service.impl;

import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.repository.PostRepository;
import com.myblog.service.PostService;
import javafx.geometry.Pos;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;


    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // Dto convert into entity
        Post post= mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        // now we have to convert the entity obj into dto
        PostDto newPostDto=mapToDto(newPost);
        return newPostDto;
    }


    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir ) {
       // Sort.by(sortBy) here we convert string into sort object
        // Pageable pageable= PageRequest.of(pageNo, pageSize,Sort.by(sortBy)) is example of method overloading
        // sortDir this holding value asc the ascending() methods going to run
        // below instead of if else we r using Conditional statement==> ternary operartor
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();// sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) if true then  run this-> Sort.by(sortBy).ascending()
        /*
             Sort sort=null;
               if(sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()){
               Sort.by(sortBy).ascending();
               }
               else{
               Sort.by(sortBy).descending();
               }
        */
        Pageable pageable= PageRequest.of(pageNo, pageSize,sort);//we cant put sortBy directly into .of() method this will take object of sort class
        Page<Post> posts = postRepository.findAll(pageable); // now we cant return post we have to convert into postDto and when u pass paeable in findAll(pageable) then returntype will change to Page this method will give u page of objects
       // page have buildin method in which current page I am in like 1st page, 2nd page, 3rd page
        List<Post> content = posts.getContent();// here all pages get converted into list


        List<PostDto> postDtos = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());// here we r returning list of dto
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(postDtos);// List<PostDto> content object getting initialising with post content
        postResponse.setPageNo(posts.getNumber());
        postResponse.setTotalPages(posts.getTotalPages());// why I am initialising post response once object has all the content I can display that in postman by changing return type of this method
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id)

        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostByid(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        postRepository.delete(post); // methos delete only take entity object not an id to delete
    }

    // Convert dto into entity object
    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);// it will copy he content from DTO and paste into Post class

//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
   PostDto mapToDto(Post post) { // I want to save data give response back to postman what it has save with status code
       PostDto postDto = modelMapper.map(post, PostDto.class);
       //PostDto postDto=new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto ;
   }
}
