package com.ksundaysky.controller;

import com.ksundaysky.model.Role;
import com.ksundaysky.model.User;
import com.ksundaysky.model.Workdays;
import com.ksundaysky.service.RoleService;
import com.ksundaysky.service.UserService;
import com.ksundaysky.service.WorkdaysService;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private WorkdaysService workdaysService;

    @RequestMapping(value = {"/users/create"}, method = RequestMethod.GET)
    public ModelAndView createNewUser() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        int roleId = 0;
        modelAndView.addObject("user", user);
        modelAndView.addObject("user.role_id_int", roleId);
        modelAndView.setViewName("/users/create");
        List<Role> roleList = roleService.findAll();
        modelAndView.addObject("role_map", roleList);

        return modelAndView;
    }


    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Ten email został już przypisany do pracownika");
        }
        if (bindingResult.hasErrors()) {
            List<Role> roleList = roleService.findAll();
            modelAndView.addObject("role_map", roleList);
            modelAndView.setViewName("/users/create");
        } else {
            String[] workdays = user.getSelectedWorkdays();
            Workdays w = new Workdays();

            for(String day : workdays)
            {
                switch (day){
                    case "Monday": w.setMonday(true); break;
                    case "Tuesday": w.setTuesday(true); break;
                    case "Wednesday": w.setWednesday(true); break;
                    case "Thursday": w.setThursday(true); break;
                    case "Friday": w.setFriday(true); break;

                }
            }
            workdaysService.saveWorkdays(w);
            user.setWorkdays_id(w.getId());
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Pracownik został dodany");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/home");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/users/edit/{id}")
    public ModelAndView editUser(@PathVariable int id) {
        User user = userService.findUserById(id);
        Workdays workdays = workdaysService.getWorkdaysById(user.getWorkdays_id());

        int howMany_selected=0;
        if(workdays.isMonday())  howMany_selected++;
        if(workdays.isTuesday()) howMany_selected++;
        if(workdays.isWednesday()) howMany_selected++;
        if(workdays.isThursday()) howMany_selected++;
        if(workdays.isFriday()) howMany_selected++;

        String[] selected= new String[howMany_selected];
        if(workdays.isMonday())  selected[--howMany_selected]="Monday";
        if(workdays.isTuesday()) selected[--howMany_selected]="Tuesday";
        if(workdays.isWednesday()) selected[--howMany_selected]="Wednesday";
        if(workdays.isThursday()) selected[--howMany_selected]="Thursday";
        if(workdays.isFriday()) selected[--howMany_selected]="Friday";

        user.setSelectedWorkdays(selected);

        ModelAndView modelAndView = new ModelAndView();
        if(user == null){
            modelAndView.addObject("errorMessage", "Użytkownik o danym id nie istnieje");
        }
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/users/edit");
        List<Role> roleList = roleService.findAll();
        Map<Integer, String> roleMap = roleList.stream().collect(Collectors.toMap(Role::getId, Role::getRole));
        modelAndView.addObject("role_map", roleList);
        modelAndView.addObject("workdays",workdays);
        //return new ModelAndView("/users/edit","user",user);
        return modelAndView;
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ModelAndView updateUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserById(user.getId());
        if (userExists != null) {
            String[] workdays = user.getSelectedWorkdays();
            Workdays w = workdaysService.getWorkdaysById(userExists.getWorkdays_id());
            w.setMonday(false);
            w.setThursday(false);
            w.setTuesday(false);
            w.setWednesday(false);
            w.setFriday(false);
            for(String day : workdays)
            {
                switch (day){
                    case "Monday": w.setMonday(true); break;
                    case "Tuesday": w.setTuesday(true); break;
                    case "Wednesday": w.setWednesday(true); break;
                    case "Thursday": w.setThursday(true); break;
                    case "Friday": w.setFriday(true); break;

                }
            }
            workdaysService.saveWorkdays(w);
            user.setWorkdays_id(w.getId());
            userExists.setEmail(user.getEmail());
//            userExists.setRoles(user.getRoles());
            userExists.setRole_id(user.getRole_id());
            userExists.setName(user.getName());
            userExists.setLastName(user.getLastName());
            userExists.setExperience(user.getExperience());
            userExists.setJobStartingDate(user.getJobStartingDate());
            userService.updateUser(userExists);
        }
        modelAndView.addObject("successMessage", "Pracownik został edytowany");
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("/home");

        return modelAndView;
    }

    @RequestMapping(value = {"/users/delete/{userId}"}, method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable int userId) {

        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserById(userId);
        Workdays workdays = workdaysService.getWorkdaysById(user.getWorkdays_id());
        if(user == null){
            modelAndView.addObject("errorMessage", "Użytkownik o danym id nie istnieje");
        }
        modelAndView.addObject("workdays",workdays);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/users/delete");

        return modelAndView;
    }


    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(name="roleId", required=true, defaultValue="0") Integer roleId) {

        ModelAndView modelAndView = new ModelAndView();
        List<Role> roles = roleService.findAll();
        modelAndView.addObject("roles", roles);
        List<User> users;
        if (roleId == 0) {
            users = userService.findAll();
        } else {
            users = userService.findAll().stream()
                    .filter(user -> user.getRole_id() == roleId)
                    .collect(Collectors.toList());
        }

        modelAndView.addObject("users", users);
        modelAndView.setViewName("/users/index");
        return modelAndView;
    }



    @RequestMapping(value = {"/users/delete/accept/{userId}"}, method = RequestMethod.GET)
    public ModelAndView deleteAccept(@PathVariable int userId) {

        ModelAndView modelAndView = new ModelAndView();
        User user= userService.findUserById(userId);
        workdaysService.deleteById(user.getWorkdays_id());
        userService.deleteById(userId);
        modelAndView.setViewName("/home");
        modelAndView.addObject("successMessage", "Pracownik został usunięty");
        return modelAndView;

    }


    @RequestMapping(value = "/users/view/{id}")
    public ModelAndView viewUser(@PathVariable int id) {
        User user = userService.findUserById(id);
        Workdays workdays = workdaysService.getWorkdaysById(user.getWorkdays_id());
        Role role = roleService.getRoleById(user.getRole_id());
        ModelAndView modelAndView = new ModelAndView();
        if(user == null){
            modelAndView.addObject("errorMessage", "Użytkownik o danym id nie istnieje");
        }
        modelAndView.addObject("role",role);
        modelAndView.addObject("workdays",workdays);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/users/view");
        return modelAndView;


    }

}