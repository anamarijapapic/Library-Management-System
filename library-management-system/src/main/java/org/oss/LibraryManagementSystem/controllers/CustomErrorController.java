package org.oss.LibraryManagementSystem.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        var statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString();
        var errorPage = new ModelAndView("error");
        errorPage.addObject("errorMsg", statusCode);
        return errorPage;
    }

}
