package org.launchcode.PickMyFood.controllers;

import org.launchcode.PickMyFood.models.Histories;
import org.launchcode.PickMyFood.models.Menu;
import org.launchcode.PickMyFood.models.User;
import org.launchcode.PickMyFood.models.Item;
import org.launchcode.PickMyFood.models.data.HistoryDao;
import org.launchcode.PickMyFood.models.data.ItemDao;
import org.launchcode.PickMyFood.models.data.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value = "item")
public class itemController {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private HistoryDao historyDao;

    @RequestMapping(value = "")
    public String index(Model model, Authentication authentication) {

        ArrayList<Item> inItems = new ArrayList<>();
        ArrayList<Item> outItems = new ArrayList<>();
        List<Item> items = new ArrayList<>();

        for (Item item : //itemDao.findAllByUserId(((User)authentication.getPrincipal()).getId())) {
            itemDao.findAllByUserId(((User)authentication.getPrincipal()).getId())){
            items.add(item);
        }
        Collections.sort(items);

        for (Item item : items) {
            if (item.isInventory()) {
                inItems.add(item);
            } else {
                outItems.add(item);
            }
        }

        model.addAttribute("inItems", inItems);
        model.addAttribute("outItems", outItems);
        model.addAttribute("items", itemDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));
        model.addAttribute("title", "PickMyFood Inventory");

        return "item/index";
    }

    @RequestMapping(value = "add")
    public String displayAdditem(Model model, Authentication authentication) {
        model.addAttribute("title", "PickMyFood Add Item");
        model.addAttribute(new Item());
        model.addAttribute("menus",
                menuDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));
        return "item/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddItem(Model model,
                                 @ModelAttribute @Valid Item item,
                                 Errors errors,
                                 @RequestParam int menuId,
                                 Authentication authentication){
        if(errors.hasErrors()) {
            model.addAttribute("title", "PickMyFood Add Item");
            model.addAttribute("menus",
                    menuDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));
            model.addAttribute("error", "");
            return "/item/add";
        }


        Menu cat = menuDao.findById(menuId).orElse(null);
        Item newItem = new Item(cat, item.getNumber(), item.getOther(),
                ((User) authentication.getPrincipal()).getId());

        for (Item aItem : itemDao.findAllByUserId(((User)authentication.getPrincipal()).getId())){
            if (aItem.getMenu().getName().equals(newItem.getMenu().getName())){
                if(aItem.getNumber().equals(newItem.getNumber())){
                    model.addAttribute("title", "PickMyFood Add Item");
                    model.addAttribute("menus",
                            menuDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));
                    model.addAttribute("error",
                            "an item of that menu and number already exists");
                    return "/item/add";
                }
            }
        }

        itemDao.save(newItem);
        Histories newHistory = new Histories("item", newItem.getMenu().getName(),
                "added", newItem.getNumber(), ((User)authentication.getPrincipal()).getId());
        historyDao.save(newHistory);
        return "redirect:/item";
    }

    @RequestMapping(value = "remove-item/{itemId}")
    public String processRemoveItems(@PathVariable int itemId, Authentication authentication) {
        Item item = itemDao.findById(itemId).orElse(null);
        Histories newHistory = new Histories("item", item.getMenu().getName(),
                "removed", item.getNumber());
        newHistory.setMyDate(newHistory.getMyDate());
        newHistory.setUserId(((User)authentication.getPrincipal()).getId());
        historyDao.save(newHistory);
        itemDao.delete(item);
        return "redirect:/item";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String displayEditForm( Model model, @PathVariable int id, Authentication authentication) {

        Item item = itemDao.findById(id).orElse(null);;
        model.addAttribute(item);
        model.addAttribute("menus",
                menuDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));
        model.addAttribute("title", "PickMyFood Edit Item ");
        return "item/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEditForm(@RequestParam int id,
                                  @ModelAttribute @Valid Item newItem,
                                  Errors errors, Model model, Authentication authentication) {
        Item aItem = itemDao.findById(id).orElse(null);;

        if (errors.hasErrors()) {
            model.addAttribute(aItem);
            model.addAttribute("menus",
                    menuDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));
            model.addAttribute("title", "PickMyFood Edit Item");
            return "redirect:/item/edit/" + id;
        }
        aItem.setOther(newItem.getOther());
        itemDao.save(aItem);
        Histories newHistory = new Histories("item", aItem.getMenu().getName(),
                "comment edited", aItem.getNumber());
        newHistory.setMyDate(newHistory.getMyDate());
        newHistory.setUserId(((User)authentication.getPrincipal()).getId());
        historyDao.save(newHistory);
        return "redirect:";

    }

}