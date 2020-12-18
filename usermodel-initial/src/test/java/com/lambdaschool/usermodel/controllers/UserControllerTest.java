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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@WebAppConfiguration
public class UserControllerTest
{
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	private User userFromJsonString(String json, Class<User> userClass) throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, userClass);
	}
	
	private User[] userArrayFromJsonString(String json, Class<User[]> userArrayClass) throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, User[].class);
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
		User[] userList = userArrayFromJsonString(content, User[].class);
		assertTrue(userList.length > 0);
	}
	
	@Test
	public void getUserById() throws Exception
	{
		String uri = "/users/user/4";
		MvcResult result =
				mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = result.getResponse().getContentAsString();
		User user = userFromJsonString(content, User.class);
		assertEquals("admin", user.getUsername());
	}
	
	@Test
	public void getUserByName() throws Exception
	{
		String uri = "/users/user/name/admin";
		MvcResult result =
				mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = result.getResponse().getContentAsString();
		User user = userFromJsonString(content, User.class);
		assertEquals("admin@lambdaschool.local", user.getPrimaryemail());
	}
	
	@Test
	public void getUserLikeName() throws Exception
	{
		String uri = "/users/user/name/like/in";
		MvcResult result =
				mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = result.getResponse().getContentAsString();
		User[] users = userArrayFromJsonString(content, User[].class);
		assertTrue(users.length == 2);
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