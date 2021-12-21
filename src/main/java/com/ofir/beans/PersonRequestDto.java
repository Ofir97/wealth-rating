package com.ofir.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PersonRequestDto {

	private Long id;
	private PersonalInfo personalInfo;
	private FinancialInfo financialInfo;
	
}
