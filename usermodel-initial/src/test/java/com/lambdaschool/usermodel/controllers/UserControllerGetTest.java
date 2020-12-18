package com.lambdaschool.usermodel.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type User controller test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@WebAppConfiguration
public class UserControllerGetTest
{
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserService userService;
	
	private MockMvc mockMvc;
	
	/**
	 * Sets up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}
	
	/**
	 * List all users.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void listAllUsers() throws Exception
	{
		String uri = "/users/users";
		MvcResult result =
				mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = result.getResponse().getContentAsString();
		User[] users = objectMapper.readValue(content, User[].class);
		assertTrue(users.length > 0);
	}
	
	/**
	 * Gets user by id.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void getUserById() throws Exception
	{
		String uri = "/users/user/4";
		MvcResult result =
				mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = result.getResponse().getContentAsString();
		User user = objectMapper.readValue(content, User.class);
		assertEquals("admin", user.getUsername());
	}
	
	/**
	 * Gets user by name.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void getUserByName() throws Exception
	{
		String uri = "/users/user/name/admin";
		MvcResult result =
				mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = result.getResponse().getContentAsString();
		User user = objectMapper.readValue(content, User.class);
		assertEquals("admin@lambdaschool.local", user.getPrimaryemail());
	}
	
	/**
	 * Gets user like name.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void getUserLikeName() throws Exception
	{
		String uri = "/users/user/name/like/in";
		MvcResult result =
				mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = result.getResponse().getContentAsString();
		User[] users = objectMapper.readValue(content, User[].class);
		assertTrue(users.length == 2);
	}
}