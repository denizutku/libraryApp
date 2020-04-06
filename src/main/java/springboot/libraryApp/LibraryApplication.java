package springboot.libraryApp;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springboot.libraryApp.Repositories.RoleRepository;
import springboot.libraryApp.Repositories.UserRepository;
import springboot.libraryApp.models.Role;
import springboot.libraryApp.models.User;

import java.util.Collections;
import java.util.Set;

@SpringBootApplication
public class LibraryApplication {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	public LibraryApplication(RoleRepository roleRepository, UserRepository userRepository) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			roleRepository.save(new Role((long) 1,"ADMIN"));
			roleRepository.save(new Role((long) 2,"USER"));
		};
	}

}
