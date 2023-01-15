package com.bookrestAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class BookRestApiApplication /*implements CommandLineRunner*/{

//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
//	
//	@Autowired
//	private UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BookRestApiApplication.class, args);	
	}
	
//	public void run(String... args) throws Exception{
//		User u1 = new User();
//		u1.setUsername("Shivam");
//		u1.setPassword(this.bCryptPasswordEncoder.encode("12345"));
//		u1.setEmail("shivam@gmail.com");
//		u1.setRole("ROLE_ADMIN");
//		this.userRepository.save(u1);
//		
//		User u2 = new User();
//		u2.setUsername("Ayush");
//		u2.setPassword(this.bCryptPasswordEncoder.encode("67890"));
//		u2.setEmail("ayush@gmail.com");
//		u2.setRole("ROLE_REGULAR");
//		this.userRepository.save(u2);
//	}
}