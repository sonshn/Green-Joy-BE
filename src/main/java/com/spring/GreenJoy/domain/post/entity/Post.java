package com.spring.GreenJoy.domain.post.entity;

import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Posts")
@Entity
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(length = 50)
    private String title;

    private String content;

    private String image1;

    private String image2;

    private String image3;

    @ManyToOne
    private User user;

}
