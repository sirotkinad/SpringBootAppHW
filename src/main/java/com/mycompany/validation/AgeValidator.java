package com.mycompany.validation;

import com.mycompany.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AgeValidator implements Validator {

    private static final String AGE_PATTERN = "^[0-9]{2,3}$";
    private static final int MIN_AGE = 18;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors e) {
        User user = (User) obj;
        int age = user.getAge();
        if(age < MIN_AGE && !(String.valueOf(age).matches(AGE_PATTERN))){
            e.rejectValue("age", "user.age.notCorrect");
        }
    }
}
