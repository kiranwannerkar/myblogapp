package com.myblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.persistence.*;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data// give us getter and setter method
@Table(name = "users",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),// userName if u write u will get BeanCreationException bcz etity class fiel name and table collumn name are not same so u have to use snake case
        @UniqueConstraint(columnNames = {"email"})
})
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String password;
    private String username;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)//FetchType.EAGER ==> it will load all the entity in memory before requirement of table in case of Lazy when it required ten only it get loaded
    @JoinTable(name = "user_roles", // this is mediator table which will join user and role table
          joinColumns = @JoinColumn(name = ("user_id"), referencedColumnName = "id"),// user_id will map to id collmn of User table
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")// role_id will map to id collmn of Role table
    )
    private Set<Role> roles;
}
