package org.example.newsfeed_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;  // 팔로잉 된 유저

    @Setter
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower; // 팔로우 한 유저 (본인)
}
