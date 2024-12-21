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

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;  // 팔로잉 된 유저

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower; // 팔로우 한 유저 (본인)

    public Follow(User follower, User following){
        this.following = following;
        this.follower = follower;
    }

    public Follow() {}
}
