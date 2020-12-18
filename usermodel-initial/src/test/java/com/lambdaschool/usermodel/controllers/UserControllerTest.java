package com.lambdaschool.usermodel.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type User controller test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest
{
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserService userService;
	
	private List<User> userList = new ArrayList<>();
	
	/**
	 * Sets up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception
	{
		Role r1 = new Role("admin");
		Role r2 = new Role("user");
		Role r3 = new Role("data");
		
		r1.setRoleid(1);
		r2.setRoleid(2);
		r3.setRoleid(3);
		
		// admin, data, user
		User u1 = new User("testadmin",
				"password",
				"testadmin@lambdaschool.local");
		u1.setUserid(5);
		u1.getRoles()
				.add(new UserRoles(u1,
						r1));
		u1.getRoles()
				.add(new UserRoles(u1,
						r2));
		u1.getRoles()
				.add(new UserRoles(u1,
						r3));
		u1.getUseremails()
				.add(new Useremail(u1,
						"testadmin@email.local"));
		u1.getUseremails()
				.add(new Useremail(u1,
						"testadmin@mymail.local"));
		userList.add(u1);
		
		// data, user
		User u2 = new User("testcinnamon",
				"1234567",
				"testcinnamon@lambdaschool.local");
		u2.setUserid(6);
		u2.getRoles()
				.add(new UserRoles(u2,
						r2));
		u2.getRoles()
				.add(new UserRoles(u2,
						r3));
		u2.getUseremails()
				.add(new Useremail(u2,
						"testcinnamon@mymail.local"));
		u2.getUseremails()
				.add(new Useremail(u2,
						"testhops@mymail.local"));
		u2.getUseremails()
				.add(new Useremail(u2,
						"testbunny@email.local"));
		userList.add(u2);
		
		// user
		User u3 = new User("testbarnbarn",
				"ILuvM4th!",
				"testbarnbarn@lambdaschool.local");
		u3.setUserid(7);
		u3.getRoles()
				.add(new UserRoles(u3,
						r2));
		u3.getUseremails()
				.add(new Useremail(u3,
						"testbarnbarn@email.local"));
		userList.add(u3);
		
		User u4 = new User("testputtat",
				"password",
				"testputtat@school.lambda");
		u4.setUserid(8);
		u4.getRoles()
				.add(new UserRoles(u4,
						r2));
		userList.add(u4);
		
		User u5 = new User("testmisskitty",
				"password",
				"testmisskitty@school.lambda");
		u5.setUserid(9);
		u5.getRoles()
				.add(new UserRoles(u5,
						r2));
		userList.add(u5);
		
		MockitoAnnotations.initMocks(this);
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
	 * Add new user.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void addNewUser() throws Exception
	{
		String uri = "/users/user";
		
		String username = "testuser";
		String password = "testpass";
		String email = "test@email.com";
		String secondaryEmail = "test2@email.com";
		
		User newUser = new User();
		newUser.setUserid(0);
		newUser.setUsername(username);
		newUser.setPassword(password);
		newUser.setPrimaryemail(email);
		
		Useremail useremail = new Useremail(newUser, secondaryEmail);
		newUser.getUseremails().add(useremail);
		
		Role role = new Role("testrole");
		UserRoles userRoles = new UserRoles(newUser, role);
		newUser.getRoles().add(userRoles);
		
		Mockito.when(userService.save(any(User.class))).thenReturn(newUser);
		
		String newUserJsonString = objectMapper.writeValueAsString(newUser);
		
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
				.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(newUserJsonString);
		
		mockMvc.perform(mockHttpServletRequestBuilder)
				.andExpect(status().isCreated());
	}
	
	/**
	 * Update full user.
	 */
	@Test
	public void updateFullUser()
	{
	}
	
	/**
	 * Update user.
	 */
	@Test
	public void updateUser()
	{
	}
	
	/**
	 * Delete user by id.
	 */
	@Test
	public void deleteUserById()
	{
	}
}