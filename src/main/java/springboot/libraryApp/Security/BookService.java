package springboot.libraryApp.Security;

import org.springframework.stereotype.Service;
import springboot.libraryApp.models.Book;
import springboot.libraryApp.models.User;

@Service
public interface BookService {
    public Book takeBook(Long id, User user);
    public Book returnBook(Long id, User user);

}
