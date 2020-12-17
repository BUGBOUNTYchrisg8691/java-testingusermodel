package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplicationTest;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplicationTest.class, properties = {"command.line.runner.enabled=false"})
public class UserServiceImplUnitTestNoDB
{
	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userrepos;
	
	private List<User> userList = new ArrayList<>();
	
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
		u1.setUserid(4);
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
		u2.setUserid(5);
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
		u3.setUserid(6);
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
		u4.setUserid(7);
		u4.getRoles()
				.add(new UserRoles(u4,
						r2));
		userList.add(u4);
		
		User u5 = new User("testmisskitty",
				"password",
				"testmisskitty@school.lambda");
		u5.setUserid(8);
		u5.getRoles()
				.add(new UserRoles(u5,
						r2));
		userList.add(u5);
		
		MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void tearDown() throws Exception
	{
	}
	
	@Test
	public void findUserById()
	{
		Mockito.when(userrepos.findById(4L)).thenReturn(Optional.of(userList.get(0)));
		assertEquals("testadmin", userService.findUserById(4).getUsername());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void findUserByIdNotFound()
	{
		Mockito.when(userrepos.findById(4L)).thenReturn(Optional.empty());
		assertEquals("testadmin", userService.findUserById(4).getUsername());
	}
	@Test
	public void findByNameContaining()
	{
		Mockito.when(userrepos.findByUsernameContainingIgnoreCase("in")).thenReturn(userList);
		assertEquals(5, userService.findByNameContaining("in").size());
	}
	
	@Test
	public void findAll()
	{
		Mockito.when(userrepos.findAll()).thenReturn(userList);
		assertEquals(5, userService.findAll().size());
	}
	
	@Test
	public void delete()
	{
		Mockito.when(userrepos.findById(4L)).thenReturn(Optional.of(userList.get(0)));
		Mockito.doNothing().when(userrepos).deleteById(4L);
		userService.delete(4L);
		assertEquals(5, userList.size());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void deleteNotFound()
	{
		Mockito.when(userrepos.findById(4L)).thenReturn(Optional.of(userList.get(0)));
		Mockito.doNothing().when(userrepos).deleteById(4L);
		userService.delete(4L);
		assertEquals(5, userList.size());
	}
	@Test
	public void findByName()
	{
		Mockito.when(userrepos.findByUsername("testmisskitty")).thenReturn(userList.get(4));
		assertEquals("testmisskitty", userService.findByName("testmisskitty").getUsername());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void findByNameNotFound()
	{
		Mockito.when(userrepos.findByUsername("testmisskitty")).thenReturn(null);
		assertEquals("testmisskitty", userService.findByName("testmisskitty").getUsername());
	}
	@Test
	public void save()
	{
	}
	
	@Test
	public void update()
	{
	}
	
	@Test
	public void deleteAll()
	{
	}
}