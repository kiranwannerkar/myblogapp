package com.myblog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data // Dto is like PoJo classs
public class PostDto {
    private long id;
    @NotEmpty (message = "Is Mandatory")// it mean we cant keep this field blank
    @Size(min = 2,message = "Post title atleast should have 2 characters")
    private String title;
    @Size(min = 10,message = "Post description atleast should have 10 characters")
    private String description;
    @Size(min = 10,message = "Post content atleast should have 10 characters")
    private String content;

}
