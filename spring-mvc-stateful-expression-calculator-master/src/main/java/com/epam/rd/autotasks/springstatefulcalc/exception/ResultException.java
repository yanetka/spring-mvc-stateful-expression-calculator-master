package com.epam.rd.autotasks.springstatefulcalc.exception;

public class ResultException extends Exception {

    public ResultException() {
    }

    public ResultException(String message) {
        super(message);
    }

    public ResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultException(Throwable cause) {
        super(cause);
    }
}
