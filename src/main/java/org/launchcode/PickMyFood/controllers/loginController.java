package org.launchcode.PickMyFood.controllers;

/**
 * Created by O.J SHIN
 */


import org.launchcode.PickMyFood.models.User;
import org.launchcode.PickMyFood.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class loginController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private TaskDao taskDao;


    @GetMapping
    public String index(@ModelAttribute @Valid User user, Errors errors,  Model model, Authentication authentication) {

        model.addAttribute("user", new User());
        model.addAttribute("title", "PickMyFood Inventory Login");

        return "login/index";
    }
    @PostMapping
    public String processLogin(){
        return "redirect:/home";
    }
}