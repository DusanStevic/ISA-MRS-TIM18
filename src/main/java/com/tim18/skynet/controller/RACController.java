package com.tim18.skynet.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.CarSearchDTO;
import com.tim18.skynet.dto.CompanyDTO;
import com.tim18.skynet.dto.ImageDTO;
import com.tim18.skynet.dto.RentACarSearchDTO;
import com.tim18.skynet.model.Branch;
import com.tim18.skynet.model.Car;
import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.model.HotelAdmin;
import com.tim18.skynet.model.RACAdmin;
import com.tim18.skynet.model.RACSpecialOffer;
import com.tim18.skynet.model.RentACar;
import com.tim18.skynet.service.impl.BranchServiceImpl;
import com.tim18.skynet.service.impl.CarServiceImpl;
import com.tim18.skynet.service.impl.CustomUserDetailsService;
import com.tim18.skynet.service.impl.HotelServiceImpl;
import com.tim18.skynet.service.impl.RentACarServiceImpl;
import com.tim18.skynet.service.impl.RentACarSpecialOfferServiceImpl;

@RestController
public class RACController {

	@Autowired
	private RentACarServiceImpl racService;
	
	@Autowired
	private CustomUserDetailsService userInfoService;
	
	@GetMapping(value = "/api/getRAC", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> getRAC() {
		RACAdmin user = (RACAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		RentACar rac = user.getRentacar();
		if (rac != null) {
			return new ResponseEntity<>(rac, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@RequestMapping(value = "/api/editRAC", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> editRAC(@RequestBody CompanyDTO dto) {
		RACAdmin user = (RACAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		RentACar rac = user.getRentacar();
		if(dto.getAdress().equals("") == false){
			rac.setAddress(dto.getAdress());
		}
		if(dto.getDescription().equals("") == false){
			rac.setDescription(dto.getDescription());
		}
		if(dto.getName().equals("") == false){
			rac.setName(dto.getName());
		}
		user.setRentacar(rac);
		return new ResponseEntity<>(racService.save(rac), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/editRACImage", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> editRACImage(@RequestBody ImageDTO image) {
		RACAdmin user = (RACAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		RentACar rac = user.getRentacar();
		System.out.println(image.getUrl());
		rac.setImage(image.getUrl());
		user.setRentacar(rac);
		return new ResponseEntity<>(racService.save(rac), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/racs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RentACar> getAllMy() {
		for(RentACar rac : racService.findAll()){
			System.out.println(rac.getName());
		}
		return racService.findAll();
	}
	
	@RequestMapping(value = "/api/rac", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public RentACar createRAC(@RequestBody RentACar rac) {
		return racService.save(rac);
	}
	
	@RequestMapping(value = "api/racs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> getRAC(@PathVariable(value = "id") Long racId) {
		RentACar rac = racService.findOne(racId);

		if (rac == null) {
			return ResponseEntity.notFound().build();
		}
	
		return ResponseEntity.ok().body(rac);
	}
	
	@RequestMapping(value = "/api/racs/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> deleteRAC(
			@PathVariable(value = "id") Long racId) {
			RentACar rac = racService.findOne(racId);

			if (rac != null) {
				racService.delete(racId);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	
	
	

		
		
		@Autowired
		private BranchServiceImpl branchService;
		
		@Autowired
		private CarServiceImpl vehicleService;
		
		
		
		@Autowired 
		private RentACarSpecialOfferServiceImpl racOfferService;	

		
		
		@RequestMapping( value = "/rentacars" , method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Collection<RentACar>> getAll() {

			Collection<RentACar> racs = racService.findAll();

			return new ResponseEntity<>(racs, HttpStatus.OK);
		}
		
		@GetMapping(value="/rentacars/{id}/branchs",produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Collection<Branch>> getMyBranchs(@PathVariable("id") Long id) {
			
			RentACar rac = racService.findOne(id);
			
			return new ResponseEntity<>(rac.getBranches(), HttpStatus.OK);
		}
		
		@PreAuthorize("hasRole('ROLE_RENTACAR_ADMIN')")
		@PostMapping(value="/rentacars/{id}/branchs",consumes=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Branch> addBranch(@PathVariable("id") Long id, @RequestBody Branch branch) {

			if(branch.getName().trim().equals("") || branch.getName()==null) {
				return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			RentACar rac = racService.findOne(id);
			rac.addBranch(branch);
			branch.setRac(rac);
			Branch br = branchService.save(branch);
			racService.save(rac);
			
			return new ResponseEntity<>(br, HttpStatus.OK);
		}
		
		@GetMapping(value = "/rentacars/{id}/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Collection<Car>> getMyVehicles(@PathVariable("id") Long id){
			RentACar rac = racService.findOne(id);
			
			List<Car> vehs = new ArrayList<>();
			
			for (Branch br : rac.getBranches()) {
				for (Car vehicle : br.getVehicles()) {
					vehs.add(vehicle);
				}
			}
			
			return new ResponseEntity<>(vehs, HttpStatus.OK);
		}


		
		@GetMapping(value = "/rentacars/{id}/specialOffers", produces = MediaType.APPLICATION_JSON_VALUE)
		public Collection<RACSpecialOffer> getRacsOffers(@PathVariable("id") Long id){
			RentACar rac = racService.findOne(id);
			
			return rac.getSpecialOffers();
		}
		
		@GetMapping(value = "/rentacars/{id}/specialOffers/{offerId}", produces = MediaType.APPLICATION_JSON_VALUE)
		public RACSpecialOffer getOneRacsOffer(@PathVariable("id") Long id, @PathVariable("offerId") Long offerId){
			RentACar rac = racService.findOne(id);
			for (RACSpecialOffer off : rac.getSpecialOffers()) {
				if(off.getId().equals(offerId)) {
					return off;
				}
			}
			return null;
		}
		
		@PostMapping(value = "/rentacars/{id}/specialOffers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<RACSpecialOffer> addSpecialOffer(@PathVariable("id") Long id, @RequestBody RACSpecialOffer offer){
			
			if(offer.getName().trim().equals("") || offer.getName() == null || offer.getPrice() == null || offer.getPrice().doubleValue()<0) {
				return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			RACSpecialOffer so = null;
			
			try {
				so = racOfferService.save(offer);
				RentACar rac = racService.findOne(id);
				rac.getSpecialOffers().add(so);
				so.setRacservice(rac);
				racService.save(rac);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity<>(so, HttpStatus.OK);
		}
		
		@PutMapping(value = "/rentacars/{id}/specialOffers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<RACSpecialOffer> updateOffer(@PathVariable("id") Long id, @RequestBody RACSpecialOffer offer){
			
			if(offer.getName().trim().equals("") || offer.getName()==null || offer.getPrice()== null || offer.getPrice().doubleValue()<0) {
				return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			RACSpecialOffer of = racOfferService.findOne(offer.getId());
			
			if (of == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			of.setName(offer.getName());
			of.setDescription(offer.getDescription());
			of.setPrice(offer.getPrice());
			
			RACSpecialOffer of2 = racOfferService.save(of);
			
			return new ResponseEntity<>(of2, HttpStatus.OK);
			
		}
		
		@DeleteMapping(value="/rentacars/specialOffers/{idOffer}")
		public ResponseEntity<HttpStatus> delete(@PathVariable("idOffer") Long idOffer){
			
			RACSpecialOffer offer = racOfferService.findOne(idOffer);
			
			if(offer != null) {
				racOfferService.delete(idOffer);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}
		// search
		@PostMapping(value="/rentacars/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Collection<RentACar>> getByNameAndAddress(@RequestBody RentACarSearchDTO rentACar) {
			
			if(rentACar.getPickUp() == null || rentACar.getDropOff() == null) {
				return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			Collection<RentACar> racs = racService.findByNameAndAddress(rentACar.getName(), rentACar.getAddress());

			return new ResponseEntity<>(racs, HttpStatus.OK);
		}
		
		@PostMapping(value = "/rentacars/vehicles/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Collection<Car>> searchVehicles(@RequestBody CarSearchDTO vehicleSearchDTO) {
			Collection<Car> vehs = vehicleService.search(vehicleSearchDTO);
			return new ResponseEntity<>(vehs, HttpStatus.OK);
		}
	}


