package org.launchcode.PickMyFood.controllers;


import org.launchcode.PickMyFood.models.*;
import org.launchcode.PickMyFood.models.data.HistoryDao;
import org.launchcode.PickMyFood.models.data.ItemDao;
import org.launchcode.PickMyFood.models.data.LocationDao;
import org.launchcode.PickMyFood.models.data.TaskDao;
import org.launchcode.PickMyFood.models.forms.AddLocationItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * created by O.J Shin
 */


@Controller
@RequestMapping(value = "locations")
public class locationController {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    HistoryDao historyDao;

    @Autowired
    private TaskDao taskDao;

    @RequestMapping(value = "")
    public String index(Model model, Authentication authentication){
        List<Location> locations = new ArrayList<>();
        for (Location location : locationDao.findAllByUserId(((User)authentication.getPrincipal()).getId())) {
            locations.add(location);
        }
        Collections.sort(locations);

        model.addAttribute("title", "PickMyFood Locations");
        model.addAttribute("locations", locations);
        return "locations/index";
    }

    @RequestMapping(value = "add")
    public String displayAddLocation(Model model) {
        model.addAttribute("title", "PickMyFood Add Location");
        model.addAttribute("location", new Location());
        model.addAttribute("error", "");
        return "locations/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddLocation(Model model, @ModelAttribute @Valid Location location, Errors errors,
                                    Authentication authentication) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "PickMyFood Add Location");
            model.addAttribute("error", "");
            return "locations/add";
        }
        //if user input same location name and location number, it will get error message and redirect.
        for (Location aLocation : locationDao.findAllByUserId(((User) authentication.getPrincipal()).getId())) {

            if (location.getNumber() == aLocation.getNumber()) {
                model.addAttribute("error_2", "A location already has that number");
                model.addAttribute("title", "PickMyFood Add Location");

                return "locations/add";
            }
            if (aLocation.getName().equals(location.getName().toLowerCase())) {
                model.addAttribute("error_1", "That name is already in use");
                model.addAttribute("title", "PickMyFood Add Location");

                return "locations/add";
            }
        }

            location.setName(location.getName().toLowerCase());
            location.setUserId(((User) authentication.getPrincipal()).getId());
            locationDao.save(location);
            Histories newHistory = new Histories("locations", location.getName(), "",
                    "added", location.getNumber());
            newHistory.setUserId(((User) authentication.getPrincipal()).getId());
            historyDao.save(newHistory);
            return "redirect:/locations/view/" + location.getId();
    }

    @RequestMapping(value = "view/{locationId}")
    public String viewLocation(Model model, @PathVariable int locationId) {

        //Optional<Location> location = locationDao.getId(locationId);
        //Location location = locationDao.findOne(locationId);
        Location location = locationDao.getOne(locationId);

        model.addAttribute("title", location.getName());
        model.addAttribute("items", location.getItems());
        model.addAttribute("location", location);
        return "locations/view";
    }

    @RequestMapping(value = "add-remove-item/{locationId}")
    public String addLocationItem(Model model, @PathVariable int locationId, Authentication authentication) {
        Location location = locationDao.getOne(locationId);
        Iterable<Item> items = itemDao.findAllByUserId(((User)authentication.getPrincipal()).getId());
        ArrayList<Item> itemsIn = new ArrayList<>();
        for(Item item : items) {
            if(item.isInventory()) {
                itemsIn.add(item);
            }
        }
        Collections.sort(itemsIn);
        AddLocationItemForm form = new AddLocationItemForm(itemsIn, location);
        model.addAttribute("form", form);
        model.addAttribute("hasItems", location.getItems());

        return "locations/add-remove-item";
    }

    @RequestMapping(value = "remove")
    public String displayRemoveForm(Model model, Authentication authentication) {

        Iterable<Location> locations = locationDao.findAllByUserId(((User)authentication.getPrincipal()).getId());
        for(Location location : locations) {
            if(location.getItems().size() > 0 ) {
                location.setSafe("No");
            }else {
                location.setSafe("Yes");
            }
        }
        model.addAttribute("locations", locations);
        model.addAttribute("title", "PickMyFood Remove Locations");

        return "locations/remove";
    }

    @RequestMapping(value = "remove-location/{locationId}")
    public String processRemoveItems(@PathVariable int locationId, Authentication authentication) {
        Location location = locationDao.getOne(locationId);
        for(Item item : location.getItems()){
            item.setisInventory(true);

        }
        Histories newHistory = new Histories("locations", location.getName(), "",
                "removed", location.getNumber());
        newHistory.setMyDate(newHistory.getMyDate());
        newHistory.setUserId(((User)authentication.getPrincipal()).getId());
        historyDao.save(newHistory);
        locationDao.delete(location);
        return "redirect:/locations/remove";
    }

    @RequestMapping(value = "remove-item-from-location/{locationId}/{itemId}", method = RequestMethod.GET)
    public String removeItemfromLocation(@PathVariable int locationId, @PathVariable int itemId,
                                       Authentication authentication) {
        Location theLocation = locationDao.getOne(locationId);
        Item theItem = itemDao.getOne(itemId);
        theLocation.removeItem(theItem);
        theItem.setisInventory(true);
        locationDao.save(theLocation);

        Histories newHistory = new Histories("locations", theLocation.getName(), theItem.getMenu().getName(),
                "gave back", theItem.getNumber());
        newHistory.setUserId(((User)authentication.getPrincipal()).getId());
        historyDao.save(newHistory);

        return "redirect:/locations/add-remove-item/" + locationId;
    }

    @RequestMapping(value = "add-item-to-location/{locationId}/{itemId}")
    public String addItemtoLocation(@PathVariable int locationId, @PathVariable int itemId, Authentication authentication) {

        Location theLocation = locationDao.getOne(locationId);
        Item theItem = itemDao.getOne(itemId);
        theItem.setisInventory(false);
        theLocation.addItem(theItem);
        locationDao.save(theLocation);

        Histories newHistory = new Histories("locations", theLocation.getName(), theItem.getMenu().getName(),
                "given", theItem.getNumber());
        newHistory.setUserId(((User)authentication.getPrincipal()).getId());
        historyDao.save(newHistory);

        return "redirect:/locations/add-remove-item/" + locationId;
    }/// only one item you can't duplicate

    @RequestMapping(value = "task")
    public String displayTasks(Model model, Authentication authentication) {
        model.addAttribute("tasks", taskDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));
        model.addAttribute("title", "PickMyFood Tasks");

        return "locations/tasks";
    }

}