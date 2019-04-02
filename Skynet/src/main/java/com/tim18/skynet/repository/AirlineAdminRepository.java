package com.tim18.skynet.repository;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim18.skynet.dto.AdminDTO;
import com.tim18.skynet.model.AirlineAdmin;

public interface AirlineAdminRepository extends JpaRepository<AirlineAdmin, Long>{


}
