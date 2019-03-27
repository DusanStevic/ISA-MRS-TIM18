package com.tim18.skynet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim18.skynet.model.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{
    
	List<Korisnik> findAll();
	
	

}
