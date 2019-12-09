package org.launchcode.PickMyFood.controllers;
/**
 * Created by O.J SHIN
 */


import org.launchcode.PickMyFood.models.*;
import org.launchcode.PickMyFood.models.data.*;
import org.launchcode.PickMyFood.models.forms.SearchItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;

@Controller
@RequestMapping("search")
public class searchController {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private HistoryDao historyDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private MenuDao menuDao;


    @RequestMapping(value = "")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("title", "PickMyFood Search Items");
        model.addAttribute("item", new Item());
        model.addAttribute("error", " ");
        model.addAttribute("menus",
                menuDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));

        return "search/index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processSearch(@ModelAttribute @Valid SearchItemForm searchItemForm, Errors errors, Model model,@RequestParam int menuId, Authentication authentication) {

        if (errors.hasErrors()){

            model.addAttribute("title", "PickMyFood Search Items Error");
            model.addAttribute("item", searchItemForm);
            model.addAttribute("error", "Please enter number only! No Empty, No String");
            model.addAttribute("menus", menuDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));
            return "search/index";

        }

        Menu cat = menuDao.getOne(menuId);
        SearchItemForm newSearchItemForm = new SearchItemForm(searchItemForm.getNumber());

        for (Item aItem : itemDao.findAllByUserId(((User)authentication.getPrincipal()).getId())){

            if(aItem.getMenu().getName() != null && cat.getName() != null) {
                if (aItem.getMenu().getName().equals(cat.getName() ))  {
                    if(aItem.getNumber() != null && newSearchItemForm.getNumber() != null) {
                        if (aItem.getNumber().equals(newSearchItemForm.getNumber())) {
                            return "redirect:/search/view/" + aItem.getId();
                        }
                    }
                }
            }
        }

        model.addAttribute("error", "sorry, we couldn't find that item or it does not exist");
        model.addAttribute("title", "PickMyFood Search Items Error");
        model.addAttribute("item", searchItemForm);
        model.addAttribute("menus", menuDao.findAllByUserId(((User) authentication.getPrincipal()).getId()));
        return "search/index";
    }

    @RequestMapping(value = "/view/{itemId}")
    public String searchView(Model model, @PathVariable int itemId, Authentication authentication) {
        Item item = itemDao.getOne(itemId);
        ArrayList<Histories> histories = new ArrayList<>();

        for (Location location : locationDao.findAllByUserId(((User)authentication.getPrincipal()).getId())) {
            for (Item item1 : location.getItems()) {
                if (item1.getId() == item.getId()) {
                    model.addAttribute("location", location);
                }
            }
        }

        for (Histories history : historyDao.findAllByUserId(((User)authentication.getPrincipal()).getId())) {
            if (history.getType() != null) {
                if (history.getItem().equals(item.getMenu().getName())) {
                    if(history.getNumber() != null ) {
                        if (history.getNumber().equals(item.getNumber())) {
                            histories.add(history);

                        }
                    }
                }
            }
        }
        Collections.reverse(histories);
        model.addAttribute("histories", histories);
        model.addAttribute("item", item);
        model.addAttribute("title", "PickMyFood Search Result");

        return "/search/view";
    }
}