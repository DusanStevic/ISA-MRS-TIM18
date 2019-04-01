package com.tim18.skynet.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tim18.skynet.model.RentACar;

public interface RACRepository extends JpaRepository<RentACar, Long>{

}
