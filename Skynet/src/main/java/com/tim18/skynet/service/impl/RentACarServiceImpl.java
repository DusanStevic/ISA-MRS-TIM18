package com.tim18.skynet.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim18.skynet.dto.RentACarDTO;
import com.tim18.skynet.model.RentACar;
import com.tim18.skynet.model.Car;
import com.tim18.skynet.repository.RentACarRepository;
import com.tim18.skynet.service.RentACarService;



@Service
public class RentACarServiceImpl implements RentACarService {

	@Autowired
	private RentACarRepository rentacarsRepository;

	public RentACar findOne(Long id) {
		Optional<RentACar> rac = rentacarsRepository.findById(id);

		if (rac.isPresent()) {
			return rac.get();
		}

		return null;
	}

	public List<RentACar> findAll() {
		return rentacarsRepository.findAll();
	}

	public RentACar save(RentACar rentACar) {
		return rentacarsRepository.save(rentACar);
	}

	public void delete(Long id) {
		rentacarsRepository.deleteById(id);
	}

	public Collection<RentACar> search(RentACarDTO rentACar) {

		ConcurrentMap<Long, RentACar> searchRAC = new ConcurrentHashMap<Long, RentACar>();

		for (RentACar rac : rentacarsRepository.findAll()) {
			if (rentACar.getId() == null || rentACar.getId().equals(rac.getId())) {
				if (rentACar.getAddress() == null
						|| rac.getAddress().toLowerCase().contains(rentACar.getAddress().toLowerCase())) {
					if (rentACar.getName() == null
							|| rac.getName().toLowerCase().contains(rentACar.getName().toLowerCase())) {
						searchRAC.put(rac.getId(), rac);
					}
				}
			}
		}
		return searchRAC.values();
	}

	public Collection<RentACar> findByNameAndAddress(String name, String address) {
		List<RentACar> racs = rentacarsRepository.findByNameAndAddress(name,
				address);

		List<RentACar> available = new ArrayList<RentACar>();
		boolean find = false;
		
		for (RentACar rentACar : racs) {
			find = false;
			for (Car veh : rentACar.getVehicles()) {
				if(veh.getVehicleReservations().size()==0) {
					find = true;
				}
				
				
				if(find) {
					available.add(rentACar);
					break;
				}
			}
		}

		return available;
	}

}
