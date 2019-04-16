package com.tim18.skynet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim18.skynet.model.Branch;
import com.tim18.skynet.repository.BranchRepository;
import com.tim18.skynet.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService{
	
	@Autowired
	private BranchRepository branchRepository;

	
	public Branch save(Branch b) {
		return branchRepository.save(b);
	}

	
	public Branch findOne(Long id) {
		return branchRepository.getOne(id);
	}

	
	public List<Branch> findAll() {
		return branchRepository.findAll();
	}


	public void remove(Long id) {
		branchRepository.deleteById(id);
		
	}
	

}

