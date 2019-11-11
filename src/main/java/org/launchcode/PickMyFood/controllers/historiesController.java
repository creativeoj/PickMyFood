package org.launchcode.PickMyFood.controllers;

import org.launchcode.PickMyFood.models.User;
import org.launchcode.PickMyFood.models.Histories;
import org.launchcode.PickMyFood.models.data.HistoryDao;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/histories")
public class historiesController {
    @Autowired
    private HistoryDao historyDao;

    @GetMapping
    public String index(Model model, Authentication authentication) {

        List<Histories> histories = new ArrayList<>();
        for (Histories history : historyDao.findAllByUserId(((User)authentication.getPrincipal()).getId())){
            histories.add(history);
        }
        Collections.reverse(histories);
        model.addAttribute("histories", histories);
        model.addAttribute("title", "PickMyFood All histories");

        return "histories/index";
    }

    @RequestMapping("/menus")
    public String menuHistories(Model model, Authentication authentication) {

        ArrayList<Histories> histories = new ArrayList<>();

        for (Histories history: historyDao.findAllByUserId(((User)authentication.getPrincipal()).getId())) {
            if (history.getType().equals("menu")) {
                histories.add(history);
            }
        }
        Collections.reverse(histories);
        model.addAttribute("histories", histories);
        model.addAttribute("title", "PickMyFood Menu Histories");
        return "histories/menus";
    }

    @RequestMapping("/items")
    public String itemHistories(Model model, Authentication authentication) {

        ArrayList<Histories> histories = new ArrayList<>();

        for (Histories history: historyDao.findAllByUserId(((User)authentication.getPrincipal()).getId())) {
            if (history.getType().equals("item")) {
                histories.add(history);
            }
        }
        Collections.reverse(histories);
        model.addAttribute("histories", histories);
        model.addAttribute("title", "PickMyFood Inventory Histories");
        return "histories/items";
    }

    @RequestMapping("/location-items")
    public String locationItemHistories(Model model, Authentication authentication) {

        ArrayList<Histories> histories = new ArrayList<>();

        for (Histories history: historyDao.findAllByUserId(((User)authentication.getPrincipal()).getId())) {
            if (history.getType().equals("locations") && !history.getItem().equals("")){
                histories.add(history);
            }
        }
        Collections.reverse(histories);
        model.addAttribute("histories", histories);
        model.addAttribute("title", "PickMyFood location-Items Histories");
        return "histories/location-items";
    }

    @RequestMapping("/locations")
    public String locationHistories(Model model, Authentication authentication) {

        ArrayList<Histories> histories = new ArrayList<>();

        for (Histories history: historyDao.findAllByUserId(((User)authentication.getPrincipal()).getId())) {
            if (history.getType().equals("locations") && history.getItem().equals("")) {
                histories.add(history);
            }
        }
        Collections.reverse(histories);
        model.addAttribute("histories", histories);
        model.addAttribute("title", "PickMyFood location Histories");
        return "histories/locations";
    }

    @RequestMapping("/tasks")
    public String taskHistories(Model model, Authentication authentication) {

        ArrayList<Histories> histories = new ArrayList<>();

        for (Histories history : historyDao.findAllByUserId(((User)authentication.getPrincipal()).getId())) {
            if(history.getType().equals("task")){
                histories.add(history);
            }
        }
        Collections.reverse(histories);
        model.addAttribute("histories", histories);
        model.addAttribute("title", "PickMyFood Task Histories");
        return "histories/tasks";
    }
}
