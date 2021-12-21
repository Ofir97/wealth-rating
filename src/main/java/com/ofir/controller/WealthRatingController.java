package com.ofir.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ofir.beans.Person;
import com.ofir.beans.PersonRequestDto;
import com.ofir.beans.ResponseDto;
import com.ofir.service.WealthRatingService;

@RestController
@RequestMapping("/wealth-rating")
public class WealthRatingController {
	
	private WealthRatingService service;
	
	@Autowired
	public WealthRatingController(WealthRatingService service) {
		this.service = service;
	}
	
	@PostMapping("/person")
	public ResponseEntity<?> addPerson(@RequestBody PersonRequestDto person) {
		ResponseDto responseDto = service.addPerson(person);
		if (responseDto.isSuccess()) // if the rich person has been persisted to DB
			return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
	
	@GetMapping("/person")
	public List<Person> getAllRich() {
		return service.getAllRich();
	}

	@GetMapping("/person/{id}")
	public Person getRichById(@PathVariable("id") Long id) {
		return service.getRichById(id);
	}
}
