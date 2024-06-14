package com.epam.rd.autotasks.springstatefulcalc.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class VariableValidator {

    private static final int MAX_RANGE = 10000;
    private static final String REGEX_VAR = "^[a-z]$";


    public boolean isValidVariableName(String variable) {
        Pattern pattern = Pattern.compile(REGEX_VAR);
        Matcher matcher = pattern.matcher(variable);
        return matcher.matches();
    }

    public boolean isValidVariableValue(String value) {
        if (isValidVariableName(value)) return true;
        else {
            int temp = Integer.parseInt(value);
            return Math.abs(temp) <= MAX_RANGE;
        }
    }
}
