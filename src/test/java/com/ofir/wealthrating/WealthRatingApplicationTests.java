package com.ofir.wealthrating;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ofir.beans.Person;
import com.ofir.beans.PersonRequestDto;
import com.ofir.beans.ResponseDto;
import com.ofir.controller.WealthRatingController;
import com.ofir.service.WealthRatingService;
import com.ofir.utils.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
public class WealthRatingApplicationTests {

	// used to test the rest controller
	private MockMvc mockMvc;

	@Mock
	private WealthRatingService service;

	@InjectMocks // mocking the controller (injecting the service mock into controller mock)
	private WealthRatingController wealthRatingController;

	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(wealthRatingController).build();
	}

	@Test
	public void testAddPerson() throws Exception {
		PersonRequestDto person = Utils.generateMockPersonRequest();

		Mockito.when(service.addPerson(person)).thenReturn(new ResponseDto(true, "ok"));

		mockMvc.perform(MockMvcRequestBuilders.post("/wealth-rating/person").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(person))).andExpect(status().isCreated());

		verify(service).addPerson(person);
	}

	@Test
	public void testGetAllRich() throws Exception {

		List<Person> richPeople = Utils.generateMockPersonList();

		Mockito.when(service.getAllRich()).thenReturn(richPeople);

		mockMvc.perform(get("/wealth-rating/person")).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(richPeople)));

		verify(service).getAllRich();
	}

	@Test
	public void testGetRichById() throws Exception {
		Person person = Utils.generateMockPerson();
		Mockito.when(service.getRichById(1L)).thenReturn(person);

		mockMvc.perform(get("/wealth-rating/person/1")).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(person)));

		verify(service).getRichById(1L);

	}
}
