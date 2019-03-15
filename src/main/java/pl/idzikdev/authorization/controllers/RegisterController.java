package pl.idzikdev.authorization.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.idzikdev.authorization.forms.RegisterForm;
import pl.idzikdev.authorization.services.UserService;

@Controller
public class RegisterController {

    final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterForm registerForm,
                           Model model) {
        if (!userService.registerUser(registerForm)) {
            model.addAttribute("info", "Login is already in use");
            return "register";
        }
        return "redirect:/login";
    }
}
