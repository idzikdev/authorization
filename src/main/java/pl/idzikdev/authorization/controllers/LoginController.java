package pl.idzikdev.authorization.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.idzikdev.authorization.forms.LoginForm;
import pl.idzikdev.authorization.services.UserService;

@Controller
public class LoginController {

    final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String register(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String register(@ModelAttribute LoginForm loginForm,
                           Model model) {
        UserService.LoginResponse loginResponse = userService.login(loginForm);
        if (loginResponse != UserService.LoginResponse.SUCCESS) {
            model.addAttribute("info", loginResponse);
            return "login";
        }

        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/login";
    }
}