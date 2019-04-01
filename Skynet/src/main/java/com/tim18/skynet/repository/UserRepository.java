package com.tim18.skynet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim18.skynet.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
