package com.epam.rd.autotasks.springstatefulcalc.controller;

import com.epam.rd.autotasks.springstatefulcalc.validator.ExpressionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/calc")
public class ExpressionController {

    private final ExpressionValidator expressionValidator;
    private final ResponseHandler responseHandler;

    @Autowired
    public ExpressionController(ExpressionValidator expressionValidator, ResponseHandler responseHandler) {
        this.expressionValidator = expressionValidator;
        this.responseHandler = responseHandler;
    }

    @PutMapping("/expression")
    public ResponseEntity<HttpStatus> putExpression(@RequestBody String value,
                                                    @Value("${error.message.wrong.expression}") String errorMessage,
                                                    HttpSession session) {
       ResponseEntity<HttpStatus> responseEntity;
        if (expressionValidator.isValid(value)) {
            responseEntity = responseHandler.sendResponse("expression", session);
            session.setAttribute("expression", value);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
        return responseEntity;
    }

    @DeleteMapping("/expression")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpression(HttpSession session) {
        session.setAttribute("expression", null);
    }
}
