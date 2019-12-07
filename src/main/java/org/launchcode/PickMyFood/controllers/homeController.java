package org.launchcode.PickMyFood.controllers;

/**
 * Created by O.J SHIN
 */

import org.launchcode.PickMyFood.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class homeController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private HistoryDao historyDao;


    @RequestMapping(value = "")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("title", "PickMyFood Inventory");
        return "index";
    }

    @RequestMapping(value = "/home")
    public String home(Model model, Authentication authentication) {

        model.addAttribute("title", "PickMyFood Inventory");

        return "home";
    }
}

