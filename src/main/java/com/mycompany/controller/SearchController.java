package com.mycompany.controller;

import com.mycompany.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;


import static com.mycompany.service.HelperClass.*;

@Controller
public class SearchController {

    private static final String CSV_USERS_FILE_PATH = "src/main/resources/files/users.csv";

    @GetMapping("/getinfo")
    public String showSearchForm(Model model) {
        model.addAttribute("user", new User());
        return "getinfo";
    }


    @PostMapping("/getinfo")
    public String getUserData(User user, Model model) {
        ArrayList<User> users = parseCSVtoUsers(CSV_USERS_FILE_PATH);
        int index = findByFullName(users, user.getName(), user.getSurname());
        if(index >= 0){
            model.addAttribute("user", users.get(index));
            return "userInfo";
        }
        return "noUser";
    }
}
