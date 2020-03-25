package springboot.libraryApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springboot.libraryApp.Repositories.RoleRepository;
import springboot.libraryApp.Repositories.UserRepository;
import springboot.libraryApp.Security.UserService;
import springboot.libraryApp.models.Role;
import springboot.libraryApp.models.User;

import javax.validation.Valid;
import java.util.List;

@Controller
class HomeController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    HomeController(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value={"/"})
    public String home(Model model){
        List<User> users = userRepository.findAll();
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping(value={"/login"})
    public String login(){
        return "login";
    }


    @GetMapping(value="/registration")
    public String registration(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(user);
            model.addAttribute("successMessage", "User has been registered successfully");
            model.addAttribute("user", new User());
        }
        return "login";
    }

    @GetMapping(value="/admin/registration")
    public String adminRegistration(Model model){
        User admin = new User();
        model.addAttribute("admin",admin);
        return "adminRegistration";
    }

    @PostMapping(value = "/admin/registration")
    public String createNewAdmin(@Valid User admin, BindingResult bindingResult, Model model) {
        User userExists = userService.findUserByEmail(admin.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.admin",
                            "There is already a admin registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(admin);
            model.addAttribute("successMessage", "Admin has been registered successfully");
            model.addAttribute("admin", new User());
        }
        return "login";
    }


    @GetMapping(value="/admin/adminHome")
    public String adminHome(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getName() + " (" + user.getEmail() + ")");
        model.addAttribute("adminMessage","This Page is available to Users with Admin Role");
        return "admin/adminHome";
    }

    @GetMapping(value="/user/userHome")
    public String userHome(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getName() + " " + " (" + user.getEmail() + ")");
        model.addAttribute("userMessage","This Page is available to Users with User Role");
        return "user/userHome";
    }

}