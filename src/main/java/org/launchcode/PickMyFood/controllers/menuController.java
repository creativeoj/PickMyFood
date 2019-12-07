package org.launchcode.PickMyFood.controllers;

/**
 * Created by O.J SHIN
 */

import org.launchcode.PickMyFood.models.*;
import org.launchcode.PickMyFood.models.data.HistoryDao;
import org.launchcode.PickMyFood.models.data.ItemDao;
import org.launchcode.PickMyFood.models.data.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by O.J Shin
 */

@Controller
@RequestMapping("menu")
public class menuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private HistoryDao historyDao;

    @RequestMapping(value= "")
    public String index(Model model, Authentication authentication){

        model.addAttribute("menus", menuDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));
        model.addAttribute("title", "PickMyFood Menus");

    return "menu/index";
    }

    @RequestMapping(value = "add")
    public String add(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("title", "PickMyFood Add Menu");
        model.addAttribute("other", "");

        return "/menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Menu menu, Errors errors, Authentication authentication) {
        if(errors.hasErrors()) {
            model.addAttribute("menu", menu);
            model.addAttribute("title", "PickMyFood Add Menu");
            model.addAttribute("other", "");

            return "/menu/add";
        }

        String menuName = menu.getName().toLowerCase();
        for (Menu aMenu : menuDao.findAllByUserId(((User)authentication.getPrincipal()).getId())) {
            if (aMenu.getName().equals(menuName)) {
                model.addAttribute("menu", menu);
                model.addAttribute("title", "PickMyFood Add Menu");
                model.addAttribute("other", "you already have that menu");

                return "/menu/add";
            }
        }

        menu.setName(menuName.toLowerCase());
        menu.setUserId(((User)authentication.getPrincipal()).getId());
        menuDao.save(menu);
        Histories newHistory = new Histories("menu", menu.getName(), "added");
        newHistory.setMyDate(newHistory.getMyDate());
        newHistory.setUserId(((User)authentication.getPrincipal()).getId());
        historyDao.save(newHistory);
        return "redirect:/item/";
    }

    @RequestMapping(value = "remove")
    public String displayRemove(Model model, Authentication authentication) {
        model.addAttribute("menus",
                menuDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));
        model.addAttribute("title", "PickMyFood Remove Menus");

        return "menu/remove";
    }

    @RequestMapping(value = "remove-menu/{menuId}")
    public String removeMenu(@PathVariable int menuId, Authentication authentication) {
        for (Item item : itemDao.findAllByUserId(((User)authentication.getPrincipal()).getId())) {
            for (Location location : item.getLocations()){
                location.removeItem(item);
            }
            if(item.getMenu().getId() == menuId){
                itemDao.delete(item);
            }
        }
        Menu menu = menuDao.getOne(menuId);
        Histories newHistory = new Histories("menu", menu.getName(), "removed");
        newHistory.setMyDate(newHistory.getMyDate());
        historyDao.save(newHistory);
        menuDao.deleteById(menuId);

        return "redirect:/menu";
    }
}



