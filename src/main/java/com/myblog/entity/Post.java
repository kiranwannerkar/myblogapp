package com.myblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data  // which will give u getter and setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts",uniqueConstraints = {@UniqueConstraint(columnNames={"title"})})// this means that title is unique
public class Post {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
     //@Column(unique = true)//to make tile as unique
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)// By doing this we can avoid null value to be stored in database
    private String content;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, orphanRemoval = true) //  because variable name given in post feature
    //cascade = CascadeType.ALL if changes one table it reflect to other table also  ex-> if delete post aoutomatically comments also gone
    //orphanRemoval = true ==> Once u remove post then comment will be orphan so comments automatically should get removed

    private Set<Comment> comments = new HashSet<>();

}
