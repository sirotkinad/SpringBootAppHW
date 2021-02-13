package com.mycompany.controller;

import com.mycompany.model.User;
import com.mycompany.service.MailSenderService;
import com.mycompany.validation.EmailValidator;
import com.mycompany.validation.AgeValidator;
import static com.mycompany.service.HelperClass.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    private static final String CSV_USERS_FILE_PATH = "src/main/resources/files/users.csv";

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private AgeValidator ageValidator;

    @Autowired
    private MailSenderService sender;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator, ageValidator);
    }

    @GetMapping("/user")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "user";
    }

    @PostMapping("/user")
    public String addUser(@ModelAttribute @Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user";
        }
        else if(isExists(user, CSV_USERS_FILE_PATH)){
            return "failedSubmit";
        }
        else{
            writeUserToCSV(user, CSV_USERS_FILE_PATH);
            sendWelcomeMessage(user);
            return "successSubmit";
        }
    }

    public void sendWelcomeMessage(User user){
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format("Hello, %s!", user.getName());
            sender.sendMessage(user.getEmail(), "Welcome message", message);
        }
    }

}
