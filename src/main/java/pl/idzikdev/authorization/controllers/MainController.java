package pl.idzikdev.authorization.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.idzikdev.authorization.services.UserSession;

@Controller
public class MainController {
    final private UserSession userSession;

    @Autowired
    public MainController(UserSession userSession) {
        this.userSession = userSession;
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }
    @GetMapping("/")
    public String index(){
        if(!userSession.isLogin()){
            return "redirect:/main";
        }
        return "index";
    }

    @PostMapping("/")
    public String index(@RequestParam("cityName") String cityName,
                        Model model) {
        if(!userSession.isLogin()){
            return "redirect:/login";
        }
        return "index";
    }
}
