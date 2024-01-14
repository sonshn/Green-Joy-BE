package com.spring.GreenJoy.domain.user.entity;

import com.spring.GreenJoy.global.common.BaseTime;
import com.spring.GreenJoy.global.common.NanoId;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Table(name = "Users")
@NoArgsConstructor
@Entity
public class User extends BaseTime {

    @EmbeddedId
    @AttributeOverride(name ="id", column = @Column(name = "user_id"))
    private NanoId userId;

    // 아이디
    @Column(nullable = false, length = 45)
    private String id;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(columnDefinition = "TEXT")
    private String profileImg;

    @Column(columnDefinition = "int unsigned")
    @ColumnDefault("0")
    private int credit;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    protected Role role = Role.MEMBER;

    public enum Role {
        ADMIN, MEMBER
    }

    @Builder
    public User(String id, String email, String password, String name, String nickname,
                String profileImg, Role role){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.role = role;
        // TODO: UUID?
    }

}
