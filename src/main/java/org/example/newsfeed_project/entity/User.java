package org.example.newsfeed_project.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(length = 50)
    private String email;
    private String password;
    @Column(length = 10)
    private String userName;
    @Column(length = 20)
    private String introduction;
    private Boolean status;

}
