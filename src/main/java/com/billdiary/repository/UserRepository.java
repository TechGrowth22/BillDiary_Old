package com.billdiary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.billdiary.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public UserEntity getAdminUser(@Param("userName") String userName,@Param("password") String password);
}
