package com.lambdaschool.usermodel.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@WebAppConfiguration
public class UserControllerTest
{
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	private String toJson(List<User> userList) throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(userList);
	}
	
	private User[] fromJson(String json, Class<User[]> userClass) throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, userClass);
	}
	
	@Before
	public void setUp() throws Exception
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@After
	public void tearDown() throws Exception
	{
	}
	
	@Test
	public void listAllUsers() throws Exception
	{
		String uri = "/users/users";
		MvcResult result =
				mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = result.getResponse().getContentAsString();
		User[] userList = fromJson(content, User[].class);
		assertTrue(userList.length > 0);
	}
	
	@Test
	public void getUserById()
	{
	}
	
	@Test
	public void getUserByName()
	{
	}
	
	@Test
	public void getUserLikeName()
	{
	}
	
	@Test
	public void addNewUser()
	{
	}
	
	@Test
	public void updateFullUser()
	{
	}
	
	@Test
	public void updateUser()
	{
	}
	
	@Test
	public void deleteUserById()
	{
	}
}