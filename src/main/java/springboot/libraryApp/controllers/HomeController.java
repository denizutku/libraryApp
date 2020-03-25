package springboot.libraryApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springboot.libraryApp.Security.UserService;
import springboot.libraryApp.models.User;

import javax.validation.Valid;

@Controller
class HomeController {

    private final UserService userService;

    HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value={"/", "/login"})
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