package com.ofir.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ofir.beans.Person;
import com.ofir.beans.PersonRequestDto;
import com.ofir.beans.ResponseDto;
import com.ofir.repository.RichPeopleRepo;
import com.ofir.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WealthRatingService {

	private RichPeopleRepo richPeopleRepo;
	private RestTemplate restTemplate;

	@Autowired
	public WealthRatingService(RichPeopleRepo richPeopleRepo, RestTemplate restTemplate) {
		this.richPeopleRepo = richPeopleRepo;
		this.restTemplate = restTemplate;
	}

	public ResponseDto addPerson(PersonRequestDto person) {
		try {
			// getting the asset evaluation of the person's city (calling API)
			Double cityAssetEvaluation = restTemplate.getForObject(
					String.format("central-bank/regional-info/evaluate?city=%s", person.getPersonalInfo().getCity()),
					Double.class);

			// getting the wealth threshold by calling another API
			Double wealthThreshold = restTemplate.getForObject("central-bank/wealth-threshold", Double.class);

			if (cityAssetEvaluation != null && wealthThreshold != null) {

				Double fortune = Utils.calcFortune(person.getFinancialInfo().getCash(),
						person.getFinancialInfo().getNumberOfAssets(), cityAssetEvaluation);

				if (fortune > wealthThreshold) { // person is rich
					Person personDb = Person.builder().id(person.getId())
							.firstName(person.getPersonalInfo().getFirstName())
							.lastName(person.getPersonalInfo().getLastName()).fortune(fortune).build();
					richPeopleRepo.save(personDb);
					log.info("rich person has been persisted to DB");
					return new ResponseDto(true, "person is identified as rich! and has been persisted to DB");
				} 
				
				return new ResponseDto(false, "person is not rich, thus has not been persisted to DB");
			}
			log.error("error occured while trying to get data from centeral bank");
			
		} catch (Exception e) {
			log.error("error occurred while trying to add a person in WealthRatingService.", e);
		}
		return new ResponseDto(false, "system error. please try again later");
	}

	public List<Person> getAllRich() {
		return richPeopleRepo.findAll();
	}

	public Person getRichById(Long id) {
		return richPeopleRepo.findById(id).orElse(null);
	}
}
