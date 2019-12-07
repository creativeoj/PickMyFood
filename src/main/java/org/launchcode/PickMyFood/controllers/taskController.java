package org.launchcode.PickMyFood.controllers;
/**
 * Created by O.J SHIN
 */

import org.launchcode.PickMyFood.models.Histories;
import org.launchcode.PickMyFood.models.Location;
import org.launchcode.PickMyFood.models.Task;
import org.launchcode.PickMyFood.models.data.HistoryDao;
import org.launchcode.PickMyFood.models.data.LocationDao;
import org.launchcode.PickMyFood.models.data.TaskDao;
import org.launchcode.PickMyFood.models.User;
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
import java.util.ArrayList;


@Controller
@RequestMapping("tasks")
public class taskController {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private HistoryDao historyDao;

    @Autowired
    private LocationDao locationDao;

    @RequestMapping(value = "")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("title", "PickMyFood Tasks");
        model.addAttribute("tasks", taskDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));

        return "tasks/index";
    }

    @RequestMapping(value = "add")
    public String add(Model model, Authentication authentication) {
        Task task = new Task();
        model.addAttribute("title", "PickMyFood Add Task");
        model.addAttribute("task", task);

        return "tasks/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Task task, Errors errors, Model model,
                                 Authentication authentication) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "PickMyFood Tasks");
            model.addAttribute("task", task);
            return "tasks/add";
        }
        task.setUserId(((User)authentication.getPrincipal()).getId());
        taskDao.save(task);
        Histories newHistory = new Histories("task", task.getName(),
                "added");
        newHistory.setUserId(((User)authentication.getPrincipal()).getId());
        historyDao.save(newHistory);

        return "redirect:/tasks/";
    }

    @RequestMapping(value = "/remove")
    public String remove(Model model, Authentication authentication) {
        model.addAttribute("title", "PickMyFood Tasks");
        model.addAttribute("tasks", taskDao.findAllByUserId(((User)authentication.getPrincipal()).getId()));

        return "tasks/remove";
    }

    @RequestMapping(value = "remove/{taskId}")
    public String processRemoveTask(@PathVariable int taskId, Authentication authentication) {
        Task task = taskDao.getOne(taskId);
        for (Location location : locationDao.findAll()) {
            for (Task aTask : location.getTasks()) {
                if (aTask.getName().equals(task.getName())) {
                    location.removeTask(aTask);
                    locationDao.save(location);
                    break;
                }
            }
        }

        Histories newHistory = new Histories("task", task.getName(),
                "removed");
        newHistory.setMyDate(newHistory.getMyDate());
        newHistory.setUserId(((User)authentication.getPrincipal()).getId());
        historyDao.save(newHistory);
        taskDao.delete(task);
        return "redirect:/tasks";
    }

    @RequestMapping(value = "add-remove-locations/{taskId}")
    public String addRemoveTask(@PathVariable int taskId, Model model, Authentication authentication) {
        Task task = taskDao.getOne(taskId);
        ArrayList<Location> completedLocations = new ArrayList<>();
        ArrayList<Location> incompleteLocations = new ArrayList<>();

        for (Location location : locationDao.findAllByUserId(((User)authentication.getPrincipal()).getId())) {
            incompleteLocations.add(location);
            if(location.getTasks().size() > 0) {
                for (Task aTask : location.getTasks()) {
                    if (aTask.getName().equals(task.getName())) {
                        completedLocations.add(location);
                        incompleteLocations.remove(location);
                        break;
                    }
                }
            }
        }

        model.addAttribute("completedLocations",completedLocations);
        model.addAttribute("incompleteLocations", incompleteLocations);
        model.addAttribute("task", task);
        model.addAttribute("title", task.getName());
        return "tasks/add-remove";
    }

    @RequestMapping(value = "add-task-location/{locationId}/{taskId}")
    public String addTaskLocation(@PathVariable int locationId, @PathVariable int taskId, Authentication authentication) {
        Location location = locationDao.getOne(locationId);
        Task task = taskDao.getOne(taskId);
        location.addTask(task);
        locationDao.save(location);
        Histories newHistory = new Histories("task",task.getName(), "completed");
        newHistory.setName(location.getName());
        newHistory.setUserId(((User)authentication.getPrincipal()).getId());
        historyDao.save(newHistory);

        return "redirect:/tasks/add-remove-locations/" + taskId;
    }

    @RequestMapping(value = "remove-task-location/{locationId}/{taskId}")
    public String removeTaskLocation(@PathVariable int locationId, @PathVariable int taskId, Authentication authentication){
        Location location = locationDao.getOne(locationId);
        Task task = taskDao.getOne(taskId);
        location.removeTask(task);
        locationDao.save(location);
        Histories newHistory = new Histories("task", task.getName(),
                "uncompleted");
        newHistory.setName(location.getName());
        newHistory.setMyDate(newHistory.getMyDate());
        newHistory.setUserId(((User)authentication.getPrincipal()).getId());
        historyDao.save(newHistory);

        return "redirect:/tasks/add-remove-locations/" + taskId;
    }
}

