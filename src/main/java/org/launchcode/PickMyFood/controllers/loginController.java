package org.launchcode.PickMyFood.controllers;

/**
 * Created by O.J SHIN
 */


import org.launchcode.PickMyFood.models.User;
import org.launchcode.PickMyFood.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String index(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "PickMyFood Inventory Login");

        return "login/index";

    }

    @PostMapping
    public String processLogin(){
        return "redirect:/home";
    }

/*
    public String processLogin(@ModelAttribute @Valid User user, Errors errors,
                               Model model) {
        if (errors.hasErrors()) {

            model.addAttribute("error", "Username does not exist.  please register first!");
            model.addAttribute("user", user);
            model.addAttribute("title", "PickMyFood Inventory Login");
            return "login/index";
        }
        for (User User : userDao.findAll()) {

            if (User.getName() != (user.getPassword())) || () {
                model.addAttribute("error", "Password not match with your account. Please try again");
                model.addAttribute("user", user);
                model.addAttribute("title", "PickMyFood Inventory Login");
                return "login/index";
            }
        }


        return "redirect:/home";

    }*/
}
