package springboot.libraryApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.libraryApp.Repositories.AuthorRepository;
import springboot.libraryApp.Repositories.BookRepository;
import springboot.libraryApp.models.Author;
import springboot.libraryApp.models.Book;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping()
    public String listAuthors(Model model) {
        List<Author> authorList = (List<Author>) authorRepository.findAll();
        model.addAttribute("listAllAuthors", authorList);
        return "authors";
    }

    @GetMapping("/{id}")
    public String getAuthorById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("authorDetails", authorRepository.findById(id).get());
        return "author";
    }

    @GetMapping("/newauthor")
    public String newAuthor(Model model) {
        Author newAuthor = new Author();
        model.addAttribute("newAuthor", newAuthor );
        return "newauthor";
    }

    @PostMapping("/saveAuthor")
    public String saveAuthor(@ModelAttribute("author") Author author) {
        authorRepository.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/deleteAuthor/{id}")
    public String deleteAuthor(@PathVariable(name = "id") Long id) {
        authorRepository.deleteById(id);
        return "redirect:/authors;";
    }
}
