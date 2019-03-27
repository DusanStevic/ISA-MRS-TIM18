package com.tim18.skynet.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.KorisnikDTO;
import com.tim18.skynet.model.Korisnik;
import com.tim18.skynet.service.KorisnikService;

@RestController
@RequestMapping(value="api/korisnici")
public class KorisnikController {
	@Autowired
	private KorisnikService korisnikService;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<KorisnikDTO>> getAllkorisnici() {
		
		List<Korisnik> korisnici = korisnikService.findAll();
		
		//convert korisnici to DTOs
		List<KorisnikDTO> korisniciDTO = new ArrayList<>();
		for (Korisnik k : korisnici) {
			korisniciDTO.add(new KorisnikDTO(k));
		}
		
		return new ResponseEntity<>(korisniciDTO, HttpStatus.OK);
	}
	
	
	
	/*@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<KorisnikDTO> getStudent(@PathVariable Long id){
		
		Korisnik korisnik = korisnikService.findOne(id);
		
		// korisnik must exist
		if(korisnik == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(new KorisnikDTO(korisnik), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<KorisnikDTO> saveStudent(@RequestBody KorisnikDTO korisnikDTO){
		
		Korisnik korisnik = new Korisnik();
		korisnik.setFirstName(korisnikDTO.getFirstName());
		korisnik.setLastName(korisnikDTO.getLastName());
		
		korisnik = korisnikService.save(korisnik);
		return new ResponseEntity<>(new KorisnikDTO(korisnik), HttpStatus.CREATED);	
	}
	
	@RequestMapping(method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<KorisnikDTO> updateStudent(@RequestBody KorisnikDTO korisnikDTO){
		
		
		Korisnik korisnik = korisnikService.findOne(korisnikDTO.getId()); 
		
		if (korisnik == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		korisnik.setFirstName(korisnikDTO.getFirstName());
		korisnik.setLastName(korisnikDTO.getLastName());
		
		korisnik = korisnikService.save(korisnik);
		return new ResponseEntity<>(new KorisnikDTO(korisnik), HttpStatus.OK);	
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
		
		Korisnik korisnik = korisnikService.findOne(id);
		
		if (korisnik != null){
			korisnikService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {		
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}*/
	
	
	
	
	
	
	
	
	

}
