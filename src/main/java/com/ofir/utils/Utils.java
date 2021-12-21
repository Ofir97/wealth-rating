package com.ofir.utils;

import java.util.ArrayList;
import java.util.List;

import com.ofir.beans.FinancialInfo;
import com.ofir.beans.Person;
import com.ofir.beans.PersonRequestDto;
import com.ofir.beans.PersonalInfo;

public class Utils {

	public static Double calcFortune(Double personCash, Integer numOfAssets, Double cityAssetEvaluation) {
		return personCash + numOfAssets * cityAssetEvaluation;
	}

	public static PersonRequestDto generateMockPersonRequest() {
		return PersonRequestDto.builder()
				.id(1L)
				.personalInfo(
						PersonalInfo.builder()
						.firstName("Bill")
						.lastName("Gates")
						.city("Washington")
						.build()
				)
				.financialInfo(FinancialInfo.builder()
						.cash(16000000.0)
						.numberOfAssets(50)
						.build()
				)
				.build();
	}
	
	public static Person generateMockPerson(){
		return new Person(1L, "Bill", "Gates", 10230.0);
	}
	
	public static List<Person> generateMockPersonList(){
		List<Person> richPeople = new ArrayList<>();
		richPeople.add(new Person(1L, "Bill", "Gates", 10230.0));
		richPeople.add(new Person(2L, "Steve", "Jobs", 12500.0));
		
		return richPeople;
	}
	
	
}
