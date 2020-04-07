package springboot.libraryApp;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springboot.libraryApp.Repositories.AuthorRepository;
import springboot.libraryApp.Repositories.BookRepository;
import springboot.libraryApp.Repositories.RoleRepository;
import springboot.libraryApp.Repositories.UserRepository;
import springboot.libraryApp.models.Author;
import springboot.libraryApp.models.Book;
import springboot.libraryApp.models.Role;
import springboot.libraryApp.models.User;

@SpringBootApplication
public class LibraryApplication {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;

	public LibraryApplication(RoleRepository roleRepository, UserRepository userRepository, BookRepository bookRepository, AuthorRepository authorRepository) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			roleRepository.save(new Role((long) 1,"ADMIN"));
			roleRepository.save(new Role((long) 2,"USER"));
			Author author = new Author((long) 0,"JRR Tolkien","Fantastic","A great author.");
			authorRepository.save(author);
		};
	}

}
