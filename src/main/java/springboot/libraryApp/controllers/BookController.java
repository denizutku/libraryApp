package springboot.libraryApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.libraryApp.Repositories.AuthorRepository;
import springboot.libraryApp.Repositories.BookRepository;
import springboot.libraryApp.models.Author;
import springboot.libraryApp.models.Book;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
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
    public String newBook(Model model, @RequestParam Long authorId) {
        Book newBook = new Book();
        model.addAttribute("newBook", newBook);
        Optional<Author> aut = authorRepository.findById(authorId);

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
        return "redirect:/books;";
    }




}
