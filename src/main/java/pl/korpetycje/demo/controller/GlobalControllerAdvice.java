package pl.korpetycje.demo.controller;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(){
        return "redirect:/";
    }
}
