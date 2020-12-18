package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplicationTest;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.repository.RoleRepository;
import com.lambdaschool.usermodel.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
import static org.mockito.ArgumentMatchers.any;

/**
 * The type User service impl unit test no db.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplicationTest.class, properties = {"command.line.runner.enabled=false"})
//@FixMethodOrder(MethodSorters.NAME_ASCENDING) // alphabetical
public class UserServiceImplUnitTestNoDB
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@MockBean
	private UserRepository userrepos;
	
	@MockBean
	private RoleRepository rolerepos;
	
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
	 * Find user by id.
	 */
	@Test
	public void findUserById()
	{
		Mockito.when(userrepos.findById(4L)).thenReturn(Optional.of(userList.get(0)));
		assertEquals("testadmin", userService.findUserById(4).getUsername());
	}
	
	/**
	 * Find user by id not found.
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void findUserByIdNotFound()
	{
		Mockito.when(userrepos.findById(4L)).thenReturn(Optional.empty());
		assertEquals("testadmin", userService.findUserById(4).getUsername());
	}
	
	/**
	 * Find by name containing.
	 */
	@Test
	public void findByNameContaining()
	{
		Mockito.when(userrepos.findByUsernameContainingIgnoreCase("in")).thenReturn(userList);
		assertEquals(5, userService.findByNameContaining("in").size());
	}
	
	/**
	 * Find all.
	 */
	@Test
	public void findAll()
	{
		Mockito.when(userrepos.findAll()).thenReturn(userList);
		assertEquals(5, userService.findAll().size());
	}
	
	/**
	 * Delete.
	 */
	@Test
	public void delete()
	{
		Mockito.when(userrepos.findById(4L)).thenReturn(Optional.of(userList.get(0)));
		Mockito.doNothing().when(userrepos).deleteById(4L);
		userService.delete(4L);
		assertEquals(5, userList.size());
	}
	
	/**
	 * Delete not found.
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void deleteNotFound()
	{
		Mockito.when(userrepos.findById(4L)).thenReturn(Optional.empty());
		Mockito.doNothing().when(userrepos).deleteById(4L);
		userService.delete(4L);
		assertEquals(5, userList.size());
	}
	
	/**
	 * Find by name.
	 */
	@Test
	public void findByName()
	{
		Mockito.when(userrepos.findByUsername("testmisskitty")).thenReturn(userList.get(4));
		assertEquals("testmisskitty", userService.findByName("testmisskitty").getUsername());
	}
	
	/**
	 * Find by name not found.
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void findByNameNotFound()
	{
		Mockito.when(userrepos.findByUsername("testmisskitty")).thenReturn(null);
		assertEquals("testmisskitty", userService.findByName("testmisskitty").getUsername());
	}
	
	/**
	 * Save.
	 */
	@Test
	public void save()
	{
		Role role = new Role("user");
		
		role.setRoleid(2);
		
		String userUsername = "testuser";
		
		User user = new User(userUsername,
				"password",
				"testuser@lambdaschool.local");
		user.getRoles()
				.add(new UserRoles(user,
						role));
		user.getUseremails()
				.add(new Useremail(user,
						"testuser@email.local"));
		
		Mockito.when(userrepos.save(any(User.class))).thenReturn(user);
		Mockito.when(rolerepos.findById(2L)).thenReturn(Optional.of(role));
		
		User newUser = userService.save(user);
		assertNotNull(newUser);
		assertEquals(userUsername, newUser.getUsername());
	}
	
	/**
	 * Save put.
	 */
	@Test
	public void savePut()
	{
		Role role = new Role("user");
		
		role.setRoleid(2);
		
		String userUsername = "testuser";
		
		User user = new User(userUsername,
				"password",
				"testuser@lambdaschool.local");
		user.getRoles()
				.add(new UserRoles(user,
						role));
		user.getUseremails()
				.add(new Useremail(user,
						"testuser@email.local"));
		user.setUserid(6);
		
		Mockito.when(userrepos.save(any(User.class))).thenReturn(user);
		Mockito.when(userrepos.findById(6L)).thenReturn(Optional.of(user));
		Mockito.when(rolerepos.findById(2L)).thenReturn(Optional.of(role));
		
		User newUser = userService.save(user);
		assertNotNull(newUser);
		assertEquals(userUsername, newUser.getUsername());
	}
	
	/**
	 * Save put not found.
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void savePutNotFound()
	{
		Role role = new Role("user");
		
		role.setRoleid(2);
		
		String userUsername = "testuser";
		
		User user = new User(userUsername,
				"password",
				"testuser@lambdaschool.local");
		user.getRoles()
				.add(new UserRoles(user,
						role));
		user.getUseremails()
				.add(new Useremail(user,
						"testuser@email.local"));
		user.setUserid(6);
		
		Mockito.when(userrepos.save(any(User.class))).thenReturn(user);
		Mockito.when(userrepos.findById(6L)).thenReturn(Optional.empty());
		Mockito.when(rolerepos.findById(2L)).thenReturn(Optional.of(role));
		
		User newUser = userService.save(user);
		assertNotNull(newUser);
		assertEquals(userUsername, newUser.getUsername());
	}
	
	/**
	 * Update.
	 */
	@Test
	public void update()
	{
		String newUsername = "testuser2";
		String newEmail = "testuser2@email.com";
		
		User user = new User();
		user.setUserid(5);
		user.setUsername(newUsername);
		user.setPrimaryemail(newEmail);
		
		Mockito.when(userrepos.save(any(User.class))).thenReturn(user);
		Mockito.when(userrepos.findById(5L)).thenReturn(Optional.of(user));
		
		User updatedUser = userService.update(user, 5);
		assertNotNull(updatedUser);
		assertEquals(newUsername, updatedUser.getUsername());
		assertEquals(newEmail, updatedUser.getPrimaryemail());
	}
}