package com.exam.examserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamserverApplication implements CommandLineRunner{

	// @Autowired
	// private UserService userService;

	// @Autowired
	// private BCryptPasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(ExamserverApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting Code");

		// try {
		// 	User user = new User();

		// 	user.setFirstName("Bhargav");
		// 	user.setLastName("Bhutwala");
		// 	user.setUsername("bhutwalabhargav");
		// 	user.setPassword(this.encoder.encode("abc123"));
		// 	user.setEmail("bhutwala.bhargav@gmail.com");
		// 	user.setProfile("default.png");
		// 	user.setPhone("5148157821");

		// 	Role role = new Role();

		// 	role.setId(2L);
		// 	role.setRoleName("Admin");

		// 	UserRole userRole = new UserRole();
		// 	userRole.setUser(user);
		// 	userRole.setRole(role);

		// 	Set<UserRole> userRoles = new HashSet<>();
		// 	userRoles.add(userRole);

		// 	User user2 = this.userService.createUser(user, userRoles);
		// 	System.out.println(user2.getUsername());
		// } catch (Exception e) {
		// 	throw new Exception("User Already Exists!");
		// }

	}

}
