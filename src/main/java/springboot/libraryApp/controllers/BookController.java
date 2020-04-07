package springboot.libraryApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.libraryApp.Repositories.AuthorRepository;
import springboot.libraryApp.Repositories.BookRepository;
import springboot.libraryApp.Repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final BookService bookService;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, UserService userService, UserRepository userRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.userService = userService;
        this.userRepository = userRepository;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("user",user);
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

    @GetMapping("/returnBook/{id}")
    public String returnBook(@PathVariable(name = "id") Long id,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        bookService.returnBook(id,user);
        return "redirect:/books";
    }

}
