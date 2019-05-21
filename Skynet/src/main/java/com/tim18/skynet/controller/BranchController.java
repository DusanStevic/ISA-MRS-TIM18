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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tim18.skynet.dto.BranchDTO;
import com.tim18.skynet.dto.RoomDTO;
import com.tim18.skynet.model.Branch;
import com.tim18.skynet.model.Car;
import com.tim18.skynet.model.HotelAdmin;
import com.tim18.skynet.model.RACAdmin;
import com.tim18.skynet.model.Room;
import com.tim18.skynet.service.impl.BranchServiceImpl;
import com.tim18.skynet.service.impl.CarServiceImpl;
import com.tim18.skynet.service.impl.CustomUserDetailsService;
import com.tim18.skynet.service.impl.RoomServiceImpl;



@Controller
public class BranchController {

	@Autowired
	private BranchServiceImpl branchService;
	
	@Autowired
	private CarServiceImpl carService;
	
	@Autowired
	private CustomUserDetailsService userInfoService;
	

	@GetMapping(value = "/branchs",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Branch>> getAll() {

		Collection<Branch> branches = branchService.findAll();

		return new ResponseEntity<>(branches, HttpStatus.OK);
	}
	
	
	@RequestMapping( value="/api/branch",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Branch createBranch(@Valid @RequestBody Branch branch) {
		RACAdmin user = (RACAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		branch.setRac(user.getRentacar());
		return branchService.save(branch);
		
	}
	
	@RequestMapping( value="/api/getBranchs",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Branch> getBranchs() {
		RACAdmin user = (RACAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Branch> branchs = new ArrayList<Branch>();
		for(Branch b : branchService.findAll()){
			if(b.getRac().getId() == user.getRentacar().getId()){
				branchs.add(b);
			}
		}
		return branchs;
	}
	
	@RequestMapping( value="/api/getBranchs/{rac_id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Branch> getRACBranchs(@PathVariable(value = "rac_id") Long rac_id) {
		List<Branch> branchs = new ArrayList<Branch>();
		for(Branch b : branchService.findAll()){
			if(b.getRac().getId() == rac_id){
				branchs.add(b);
			}
		}
		return branchs;
	}
	
	@RequestMapping( value="/api/getBranch/{rac_id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Branch getBranch(@PathVariable(value = "branch_id") Long branch_id) {
		for(Branch b : branchService.findAll()){
			if(b.getId() == branch_id){
				return b;
			}
		}
		return null;
	}
	
	@PreAuthorize("hasRole('ROLE_RENTACAR_ADMIN')")
	@PutMapping(value = "/branchs", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Branch> update(@RequestBody Branch br) {

		if(br.getName().trim().equals("") || br.getName()==null) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		Branch branch = branchService.findOne(br.getId());

		if (branch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		branch.setName(br.getName());
		branch.setAddress(br.getAddress());

		Branch branch2 = branchService.save(branch);
		return new ResponseEntity<>(branch2, HttpStatus.OK);
	}

	
	@RequestMapping( value="/api/deleteBranch/{rac_id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Branch deleteBranch(@PathVariable(value = "rac_id") Long rac_id) {
		for(Branch b : branchService.findAll()){
			if(b.getId() == rac_id){
				b.setRac(null);
			}
		}
		branchService.delete(rac_id);
		return null;
	}
	
	
	@GetMapping(value = "/branchs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Branch> findOne(@PathVariable("id") Long id) {

		Branch br = branchService.findOne(id);

		return new ResponseEntity<>(br, HttpStatus.OK);
	}

	@GetMapping(value = "/branchs/{id}/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Car>> getMyVehicles(@PathVariable("id") Long id) {
		Branch branch = branchService.findOne(id);

		return new ResponseEntity<>(branch.getVehicles(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_RENTACAR_ADMIN')")
	@PostMapping(value = "/branchs/{id}/vehicles", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> addVehicle(@PathVariable("id") Long id, @RequestBody Car vehicle) {

		if(vehicle.getRegistrationNumber().trim().equals("") || vehicle.getRegistrationNumber() == null) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if(carService.findByRegNumber(vehicle.getRegistrationNumber()) != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		Branch branch = branchService.findOne(id);
		branch.addVehicle(vehicle);
		vehicle.setBranch(branch);
		Car veh = carService.save(vehicle);
		branchService.save(branch);

		return new ResponseEntity<>(veh, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_RENTACAR_ADMIN')")
	@DeleteMapping(value = "/branchs/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {

		Branch branch = branchService.findOne(id);
		if (branch != null) {
			branchService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

