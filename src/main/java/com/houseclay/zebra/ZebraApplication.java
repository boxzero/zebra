package com.houseclay.zebra;

import com.houseclay.zebra.model.Role;
import com.houseclay.zebra.model.User;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class ZebraApplication implements CommandLineRunner {

	@Autowired private UserService userService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(ZebraApplication.class, args);
	}


	//Command Line Runner
	@Override
	public void run(String... args) throws Exception {
		//creating role

		userService.saveRole(new Role(null,"ROLE_USER")); // 3rd Party User - Non HouseClay People
		userService.saveRole(new Role(null,"ROLE_MANAGER")); //HouseClay Working People
		userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN")); // Ankit and Arpit Only

		userService.registerUser(new User(null,"ankit.biswas9@gmail.com","password",new ArrayList<>(),"Ankit","Biswas","7892014327",true,true,false,"No Noted mentioned", BaseTimeStamp.builder().build()),"SYSTEM");
//		userService.registerUser(new User(null,"arpit.biswas@gmail.com","password",new ArrayList<>(),"Arpit","Biswas","8910669953",true,true,false,"No Noted mentioned"));
//		userService.registerUser(new User(null,"john.doe@gmail.com","password",new ArrayList<>(),"John","Doe","7892014326",true,true,false,"No Noted mentioned"));
//		userService.registerUser(new User(null,"ravish.chauvey@gmail.com","password",new ArrayList<>(),"Ravish","Chaubey","7892013327",true,true,false,"No Noted mentioned"));


		userService.addRoleToUser("ankit.biswas9@gmail.com","ROLE_SUPER_ADMIN");
//		userService.addRoleToUser("arpit.biswas@gmail.com","ROLE_SUPER_ADMIN");
//		userService.addRoleToUser("john.doe@gmail.com","ROLE_USER");
//		userService.addRoleToUser("john.doe@gmail.com","ROLE_MANAGER");
//		userService.addRoleToUser("ravish.chauvey@gmail.com","ROLE_MANAGER");

	}

}
