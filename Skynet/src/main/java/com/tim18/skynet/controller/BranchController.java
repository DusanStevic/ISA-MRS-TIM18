package com.tim18.skynet.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tim18.skynet.model.Branch;
import com.tim18.skynet.service.impl.BranchServiceImpl;



@Controller
public class BranchController {

	@Autowired
	private BranchServiceImpl branchService;
	

	@RequestMapping( value="api/branchs",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Branch createBranch(@Valid @RequestBody Branch branch) {
		return branchService.save(branch);
	}
	
	

	@RequestMapping(value = "/api/branchs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Branch> getAllCars() {
		return branchService.findAll();
	}


	@RequestMapping(value = "/api/branchs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Branch> getBranch(
			@PathVariable(value = "id") Long branchId) {
			Branch branch = branchService.findOne(branchId);

			if (branch == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok().body(branch);
	}




	@RequestMapping(value = "/api/branchs/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Branch> deleteBranch(
			@PathVariable(value = "id") Long branchId) {
			Branch branch = branchService.findOne(branchId);

			if (branch != null) {
				branchService.remove(branchId);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
	}
}
