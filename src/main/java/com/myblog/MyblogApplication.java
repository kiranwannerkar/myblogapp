package com.myblog;

import com.myblog.entity.Role;
import com.myblog.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyblogApplication implements CommandLineRunner {// In order to create metadata implements CommandLineRunner which will give  run method this will help to prepopulate data in database the moment I deploy project online
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole); //the mpment I package projct and move to aws this data will present in role table automatically that s called meatadata in table
	}
}
