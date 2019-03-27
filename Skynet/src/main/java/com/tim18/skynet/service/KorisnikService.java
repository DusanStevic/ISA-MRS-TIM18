package com.tim18.skynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim18.skynet.model.Korisnik;
import com.tim18.skynet.repository.KorisnikRepository;



@Service
public class KorisnikService {
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	
	/*OVDE SE JAVLJA BUG*/
	
	/*
	public Korisnik findOne(Long id) {
		return korisnikRepository.findById(id);
		
	}*/

	public List<Korisnik> findAll() {
		return korisnikRepository.findAll();
	}
	
	

	public Korisnik save(Korisnik korisnik) {
		return korisnikRepository.save(korisnik);
	}

	public void remove(Long id) {
		korisnikRepository.deleteById(id);
	}
	
	
	
	
	
}
