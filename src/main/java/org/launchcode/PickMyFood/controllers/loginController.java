package org.launchcode.PickMyFood.controllers;

/**
 * Created by O.J SHIN
 */


import org.launchcode.PickMyFood.models.User;
import org.launchcode.PickMyFood.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class loginController {
    @Autowired
    private UserDao userDao;
    @GetMapping
    public String index(Model model){
        model.addAttribute("title", "Login");
        model.addAttribute("user", new User());
        return "login/index";
    }

    @PostMapping
    public String processLogin(){
        return "redirect:/";
    }

}
