package me.nano.springsecurityjwt;

import me.nano.springsecurityjwt.dto.UserDto;
import me.nano.springsecurityjwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class SpringSecurityJwtApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext(){
		return new SpringApplicationContext();
	}

	@Override
	public void run(String... args) throws Exception {
		
		UserDto newUser = new UserDto();
		newUser.setFirstName("Adnane");
		newUser.setLastName("MILIARI");
		newUser.setPhone("0600001010");
		newUser.setEmail("jwt@test.com");
		newUser.setPassword("123456789");
		newUser.setAddress("Rabat, Morocco");
		userService.createUser(newUser);
	}
}
