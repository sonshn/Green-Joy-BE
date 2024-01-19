package com.spring.GreenJoy.domain.user;

import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.NanoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, NanoId> {
}
