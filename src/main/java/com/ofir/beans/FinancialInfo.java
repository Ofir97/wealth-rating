package com.ofir.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FinancialInfo {

	private Double cash;
	private Integer numberOfAssets;
	
}
