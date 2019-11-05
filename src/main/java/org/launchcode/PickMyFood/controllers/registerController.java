package org.launchcode.PickMyFood.controllers;

/**
 * Created by O.J SHIN
 */

import org.launchcode.PickMyFood.models.User;
import org.launchcode.PickMyFood.models.data.UserDao;
import org.launchcode.PickMyFood.service.CustomUserDetailsService;
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
@RequestMapping("/register")
public class registerController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserDao userDao;


    @GetMapping
    public String index(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Register");
        return "register/index";
    }

    @PostMapping
    public String processAddUser(@ModelAttribute @Valid User user, Errors errors,
                                 Model model){
        if (errors.hasErrors()){
            model.addAttribute("user", user);
            model.addAttribute("title", "Register");
            return "register/index";
        }
        for(User aUser : userDao.findAll()){
            if (aUser.getName().equals(user.getName())) {
                model.addAttribute("error", "that name is already in use");
                model.addAttribute("user", user);
                model.addAttribute("title", "Register");
                return "register/index";
            }
        }
        //adding a confirm password
        User newUser = new User(user.getName(), user.getPassword());
        customUserDetailsService.registerNewUserAccount(newUser);

        return "redirect:/login/";
    }

}


