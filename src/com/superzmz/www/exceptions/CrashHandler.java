package com.superzmz.www.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class CrashHandler {

    @ExceptionHandler({Exception.class})
    public ModelAndView handleXXException(Exception ex){
        System.out.println("=====handleXXException=====");
        ModelAndView mv = new ModelAndView("error");
        ex.printStackTrace();
        mv.addObject("ex",ex);
        return mv;
    }


}
