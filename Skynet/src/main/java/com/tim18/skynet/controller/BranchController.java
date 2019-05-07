package com.tim18.skynet.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tim18.skynet.dto.BranchDTO;
import com.tim18.skynet.dto.RoomDTO;
import com.tim18.skynet.model.Branch;
import com.tim18.skynet.model.HotelAdmin;
import com.tim18.skynet.model.RACAdmin;
import com.tim18.skynet.model.Room;
import com.tim18.skynet.service.impl.BranchServiceImpl;
import com.tim18.skynet.service.impl.CustomUserDetailsService;
import com.tim18.skynet.service.impl.RoomServiceImpl;



@Controller
public class BranchController {

	@Autowired
	private BranchServiceImpl branchService;
	
	@Autowired
	private CustomUserDetailsService userInfoService;
	

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
	
	@RequestMapping( value="/api/editBranch",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Branch editBranch(@RequestBody BranchDTO branch) {
		for(Branch b : branchService.findAll()){
			if(b.getId() == branch.getId()){
				if(branch.getAdress() != b.getAddress()){
					b.setAddress(branch.getAdress());
				}
				if(branch.getName() != b.getName()){
					b.setName(branch.getName());
				}
				return branchService.save(b);
			}
		}
		return null;
	}
	
	@RequestMapping( value="/api/deleteBranch/{rac_id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Branch deleteBranch(@PathVariable(value = "rac_id") Long rac_id) {
		for(Branch b : branchService.findAll()){
			if(b.getId() == rac_id){
				b.setRac(null);
			}
		}
		branchService.remove(rac_id);
		return null;
	}
}