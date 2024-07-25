package com.myblog.payload;

import com.myblog.entity.Post;
import javafx.geometry.Pos;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {
    private long id;
    @NotEmpty
    @Size(min = 3,message = "Name should have atleast 2 character")
    private String name;
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 5,message = "message should have atleast 5 character")
    private String body;
//    private Post post;
}
