package springboot.libraryApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springboot.libraryApp.Repositories.AuthorRepository;
import springboot.libraryApp.Repositories.BookRepository;

@Controller
class HomeController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    HomeController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("books",bookRepository.findAll());
        model.addAttribute("authors",authorRepository.findAll());

        return "index";
    }
}
