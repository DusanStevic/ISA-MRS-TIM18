package com.tim18.skynet.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.joda.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim18.skynet.dto.DestinationBean;
import com.tim18.skynet.dto.FlightBean;
import com.tim18.skynet.dto.ImageDTO;
import com.tim18.skynet.model.Airline;
import com.tim18.skynet.model.AirlineAdmin;
import com.tim18.skynet.model.Destination;
import com.tim18.skynet.model.Flight;
import com.tim18.skynet.model.Hotel;
import com.tim18.skynet.model.HotelAdmin;
import com.tim18.skynet.model.Seat;
import com.tim18.skynet.service.AirlineAdminService;
import com.tim18.skynet.service.impl.AirlineServiceImpl;
import com.tim18.skynet.service.impl.CustomUserDetailsService;
import com.tim18.skynet.service.impl.DestinationService;
import com.tim18.skynet.service.impl.FlightService;
import com.tim18.skynet.service.impl.SeatService;

@RestController
public class AirlineController {
	

	@Autowired
	private AirlineAdminService airlineAdminService;
	@Autowired
	private CustomUserDetailsService userInfoService;
	@Autowired
	private AirlineServiceImpl airlineService;
	
	@Autowired
	private DestinationService destinationService;
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private SeatService seatService;
	
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
	
	@GetMapping(value = "/api/getAirline", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> getAirline() {
		AirlineAdmin user = (AirlineAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Airline airline = user.getAirline();
		if (airline != null) {
			return new ResponseEntity<>(airline, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	//Method for adding new flight
		@RequestMapping(value = "/api/addFlight", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
		@PreAuthorize("hasAuthority('ROLE_AIRLINE_ADMIN')")
		public ResponseEntity<?> addFlight(@RequestBody FlightBean newFlightInfo) throws Exception {

			System.out.println("Uleteo sam u dodavanje letova.");
			AirlineAdmin airlineAdmin = (AirlineAdmin) this.userInfoService
					.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			Airline a = airlineAdmin.getAirline();
			if (a == null) {
				System.out.println("Flight admin doesnt't have flight company.");
				return new ResponseEntity<>("Flight admin doesnt't have flight company.",HttpStatus.NOT_FOUND);
				
			}
			
			
			Date endDate = sdf1.parse(newFlightInfo.getEndDate_str());
			Date startDate = sdf1.parse(newFlightInfo.getStartDate_str());
			
			//PROVERA NA SEVERU
			
			if (endDate.before(startDate)) {
				//return "End date can not be before start date!";
				return new ResponseEntity<>("End date can not be before start date!",HttpStatus.BAD_REQUEST);
				
			}
			Date today = new Date();
			if (startDate.before(today) || endDate.before(today)) {
				//return "Dates can not be in the past!";
				return new ResponseEntity<>("Dates can not be in the past!",HttpStatus.BAD_REQUEST);
			}
			if (newFlightInfo.getStartDestination().equals(newFlightInfo.getEndDestination())) {
				//return "Start and end destinations must be different!";
				return new ResponseEntity<>("Start and end destinations must be different!",HttpStatus.BAD_REQUEST);
			}
			Destination startDestination = destinationService.findByName(newFlightInfo.getStartDestination());
			Destination endDestination = destinationService.findByName(newFlightInfo.getEndDestination());
			if (startDestination == null || endDestination == null) {
				return null;
			}
			
			
			Flight newFlight = new Flight(startDate, endDate, newFlightInfo.getFlightDuration(), newFlightInfo.getFlightLength(),
					newFlightInfo.getSeats(), startDestination, endDestination,
					newFlightInfo.getBusinessPrice(), newFlightInfo.getEconomicPrice(),
					newFlightInfo.getFirstClassPrice());
			
			
			flightService.save(newFlight);
			a.getFlights().add(newFlight);
			airlineService.save(a);
			airlineAdminService.save(airlineAdmin);

			return new ResponseEntity<>(newFlight, HttpStatus.CREATED);
		}
		
		
		@RequestMapping(value = "/api/editAirlineImage", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Airline> editAirlineImage(@RequestBody ImageDTO image) {
			AirlineAdmin user = (AirlineAdmin) this.userInfoService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			Airline airline = user.getAirline();
			System.out.println(image.getUrl());
			airline.setImage(image.getUrl());
			user.setAirline(airline);
			return new ResponseEntity<>(airlineService.save(airline), HttpStatus.OK);
		}
		
		
		@RequestMapping(value = "/api/flightSearch", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ArrayList<FlightBean>> searchFlights(@RequestBody FlightBean search) throws Exception {
			System.out.println("UPAO SAM U PRETRAGU LETOVA");
			System.out.println("ONO STO JE STIGLO SA FRONTA" + search.toString());
			ArrayList<Flight> flights = new ArrayList<>();
			flights = (ArrayList<Flight>) flightService.findAll();
			ArrayList<FlightBean> foundFlights = new ArrayList<>();
			DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
			String companyName = "";
			for (Flight f : flights) {
				System.out.println("LET KOJI JE PRONADJEN U BAZI:"+f.toString());
				companyName = getCompanyNameForFlight(f);
				System.out.println("IME AEROKOMPANIJE KOJA SPROVODI LET:"+companyName);
				if ((f.getStartDestination().getName().equals(search.getStartDestination())
						|| search.getStartDestination().equals(""))
						&& (f.getEndDestination().getName().equals(search.getEndDestination())
								|| search.getEndDestination().equals(""))
						&& (companyName.equals(search.getFlightCompany()) || search.getFlightCompany().equals(""))
						&& (f.getEconomicPrice() >= search.getMinEconomic() || (search.getMinEconomic() == 0))
						&& (f.getBusinessPrice() >= search.getMinBusiness() || (search.getMinBusiness() == 0))
						&& (f.getFirstClassPrice() >= search.getMinFirstClass() || (search.getMinFirstClass() == 0))
						&& (f.getEconomicPrice() <= search.getMaxEconomic() || (search.getMaxEconomic() == 0))
						&& (f.getBusinessPrice() <= search.getMaxBusiness() || (search.getMaxBusiness() == 0))
						&& (f.getFirstClassPrice() <= search.getMaxFirstClass() || (search.getMaxFirstClass() == 0))
						&& (f.getFlightDuration() == search.getFlightDuration() || search.getFlightDuration() == 0)
						&& (f.getFlightLength() == search.getFlightLength() || search.getFlightLength() == 0)
						&& (search.getStartDate() == null
								|| dateTimeComparator.compare(f.getStartDate(), search.getStartDate()) == 0)
						&& (search.getEndDate() == null
								|| dateTimeComparator.compare(f.getEndDate(), search.getEndDate()) == 0)) {

					foundFlights
							.add(new FlightBean(f, companyName, sdf2.format(f.getStartDate()), sdf2.format(f.getEndDate())));
				}
			}
			System.out.println("\tREZ = " + foundFlights.size());
			return new ResponseEntity<>(foundFlights, HttpStatus.OK);

		}	
		private String getCompanyNameForFlight(Flight flight) {
			String companyName = "";
			for (Airline a : airlineService.findAll()) {
				if (a.getFlights().contains(flight)) {
					companyName = a.getName();
					break;
				}
			}
			return companyName;
		}
	
	
	@RequestMapping(value = "/api/addDestination", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_AIRLINE_ADMIN')")
	// Method for adding new destination on which flight company operates
	public ResponseEntity<Destination>addDestination(@RequestBody DestinationBean destInfo) {
		System.out.println("Uleteo sam u dodavanje destinacije.");
		AirlineAdmin airlineAdmin = (AirlineAdmin) this.userInfoService
				.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Airline a = airlineAdmin.getAirline();
		if (a == null) {
			System.out.println("Flight admin doesnt't have flight company.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		Destination newDestination = new Destination(destInfo.getName(), destInfo.getDescription(),
				destInfo.getCoordinates());
		destinationService.save(newDestination);
		a.getDestinations().add(newDestination);
		// update flight company
		airlineService.save(a);
		//return newDestination;
		return new ResponseEntity<>(newDestination, HttpStatus.CREATED);
		
		
	}
	
	
	
	
	
	@RequestMapping(value = "/api/getDestinations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_AIRLINE_ADMIN')")
	// Method returns list of destinations on which flight company operates
	public ResponseEntity<?> getDestinations() throws Exception {
		System.out.println("ULETEO SAM U PRIKAZ SVIH DESTINACIJA");
		AirlineAdmin airlineAdmin = (AirlineAdmin) this.userInfoService
				.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Airline a = airlineAdmin.getAirline();
		if (a == null) {
			System.out.println("Flight admin doesnt't have flight company.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		
		ArrayList<Destination> destinations = new ArrayList<>();
		for (Destination d : a.getDestinations()) {
			destinations.add(d);
		}
		return new ResponseEntity<>(destinations, HttpStatus.OK);
		

		
	}
	
	
	@RequestMapping(value = "/api/getDestination/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_AIRLINE_ADMIN')")
	// Method returns list of destinations on which flight company operates
	public ResponseEntity<Destination> getDestination(@PathVariable("id")Long id){
		System.out.println("ULETEO SAM U PRIKAZ JEDNE  DESTINACIJE");
		
		Destination d = destinationService.findOne(id);
		if (d == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("ONO STO JE PRONADJENO U BAZI"+d.toString());
		return new ResponseEntity<>(d, HttpStatus.OK);
		

		
	}
	
	@RequestMapping(value = "/api/updateDestination", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Destination> editDestination(@RequestBody Destination destinationFront) {
		
		System.out.println("ULETEO SAM U UPDATE  DESTINACIJE");
		System.out.println("ONO STO JE STIGLO SA FRONTA"+destinationFront.toString());
		Destination d = destinationService.findOne(destinationFront.getId());
		
		if (d == null) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		d.setName(destinationFront.getName());
		d.setDescription(destinationFront.getDescription());
		d.setCoordinates(destinationFront.getCoordinates());
		
		Destination destinationEdited = destinationService.save(d);
		return new ResponseEntity<>(destinationEdited, HttpStatus.OK);
		

		
	}
	
	
	
	
	
	
	@RequestMapping( value="api/airline",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Airline createAirline(@Valid @RequestBody Airline airline) {
		return airlineService.save(airline);
	}
	
	
	

	
	@RequestMapping(value = "api/airlines", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Airline> getAllAirlines() {
		return airlineService.findAll();
	}

	
	@RequestMapping(value = "/api/airlines/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> getAirline(
			@PathVariable(value = "id") Long AirlineId) {
		Airline airline = airlineService.findOne(AirlineId);

		if (airline == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(airline);
	}

	
	@RequestMapping(value = "/api/airlines/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> updateAirline(
			@PathVariable(value = "id") Long AirlineId,
			@Valid @RequestBody Airline a) {

		Airline airline = airlineService.findOne(AirlineId);
		if (airline == null) {
			return ResponseEntity.notFound().build();
		}

		airline.setName(a.getName());
		airline.setAddress(a.getAddress());
		airline.setDescription(a.getDescription());
		

		Airline updateAirline = airlineService.save(airline);
		return ResponseEntity.ok().body(updateAirline);
	}

	
	@RequestMapping(value = "/api/airlines/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> deleteAirline(
			@PathVariable(value = "id") Long AirlineId) {
		Airline airline = airlineService.findOne(AirlineId);

		if (airline != null) {
			airlineService.remove(AirlineId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
