package com.mycompany.validation;

import com.mycompany.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class EmailValidator implements Validator {

    private static final String EMAIL_PATTERN = "^[a-z0-9-\\+]+(\\.[a-z0-9-]+)*@" +
            "[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$";

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors e) {
        User user = (User) obj;
        if(! user.getEmail().matches(EMAIL_PATTERN)){
            e.rejectValue("email", "user.email.notCorrect");
        }
    }
}