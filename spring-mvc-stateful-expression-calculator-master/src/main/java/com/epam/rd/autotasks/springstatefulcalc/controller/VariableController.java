package com.epam.rd.autotasks.springstatefulcalc.controller;

import com.epam.rd.autotasks.springstatefulcalc.validator.VariableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/calc")
public class VariableController {

    private final VariableValidator variableValidator;
    private final ResponseHandler responseHandler;

    @Autowired
    public VariableController(VariableValidator variableValidator, ResponseHandler responseHandler) {
        this.variableValidator = variableValidator;
        this.responseHandler = responseHandler;
    }

    @PutMapping("/{variable}")
    public ResponseEntity<HttpStatus> putVariable(@RequestBody String value,
                                                  @PathVariable String variable,
                                                  @Value("${error.message.wrong.variable}") String errorMessage,
                                                  HttpSession session) {
        ResponseEntity<HttpStatus> responseEntity;
        if (variableValidator.isValidVariableName(variable) && variableValidator.isValidVariableValue(value)) {
            responseEntity = responseHandler.sendResponse(variable, session);
            session.setAttribute(variable, value);
        } else if (!variableValidator.isValidVariableName(variable)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return responseEntity;
    }

    @DeleteMapping("/{variable}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVariable(@PathVariable("variable") String variable,
                               HttpSession session) {
        session.setAttribute(variable,null);
    }
}
