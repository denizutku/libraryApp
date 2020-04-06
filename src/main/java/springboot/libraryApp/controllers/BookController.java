package springboot.libraryApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.libraryApp.Repositories.AuthorRepository;
import springboot.libraryApp.Repositories.BookRepository;
import springboot.libraryApp.Security.BookService;
import springboot.libraryApp.Security.UserService;
import springboot.libraryApp.models.Author;
import springboot.libraryApp.models.Book;
import springboot.libraryApp.models.User;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserService userService;
    private final BookService bookService;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, UserService userService, BookService bookService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String listBooks(Model model) {
        List<Book> bookList = (List<Book>) bookRepository.findAll();
        model.addAttribute("listAllBooks", bookList);
        return "books";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("bookDetails", bookRepository.findById(id).get());
        return "book";
    }

    @GetMapping("/newbook")
    public String newBook(Model model) {
        Book newBook = new Book();
        model.addAttribute("newBook", newBook);
        List<Author> authorList = (List<Author>) authorRepository.findAll();
        model.addAttribute("author", authorList);
        return "newbook";
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable(name = "id") Long id) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/takeBook/{id}")
    public String takeBook(@PathVariable(name = "id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        bookService.takeBook(id,user);
        return "redirect:/books";
    }

}
