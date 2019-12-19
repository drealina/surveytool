package de.hsba.bi.Umfrage.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class IndexController {

    //Verweist auf die Seite index.html
    @GetMapping
    public String index() {
        return "index";
    }

    //Verweist auf die Seite login.html, verhindert den Aufruf der Seite durch angemeldete User
    @GetMapping("/login")
    public String login(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      /* // !!! for testing preset login form, remove when done developing
        model.addAttribute("userName", "Admin");
        model.addAttribute("userPassword", "admin123");
        
       */
        return auth instanceof AnonymousAuthenticationToken ? "/login" : "redirect:/";
    }
}
