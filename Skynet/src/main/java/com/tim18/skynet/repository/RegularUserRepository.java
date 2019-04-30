package com.tim18.skynet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tim18.skynet.model.RegularUser;

@Repository
public interface RegularUserRepository extends JpaRepository<RegularUser, Long>{

}

