package org.example.newsfeed_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @Column(length = 5)
    private String userName;
    @Column(length = 20)
    private String introduction;
    // 소프트 삭제(기본값 ture)
    @Setter
    private Boolean status = true;

    public User() {

    }

    public User(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

}
