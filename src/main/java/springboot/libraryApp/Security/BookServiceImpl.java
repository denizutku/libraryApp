package springboot.libraryApp.Security;

import org.springframework.stereotype.Service;
import springboot.libraryApp.Repositories.BookRepository;
import springboot.libraryApp.models.Book;
import springboot.libraryApp.models.User;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book takeBook(Long id, User user) {
        bookRepository.findById(id).get().setAvailable(false);
        bookRepository.findById(id).get().setUser(user);
        return bookRepository.save(bookRepository.findById(id).get());
    }
}
