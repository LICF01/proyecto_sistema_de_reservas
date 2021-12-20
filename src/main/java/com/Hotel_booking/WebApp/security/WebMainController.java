package com.Hotel_booking.WebApp.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
    public class WebMainController {

    @RequestMapping(value = { "/", "/{x:[\\w\\-]+}", "/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}" })
    public String getIndex(HttpServletRequest request) {
        return "/index.html";
    }
}

//  This controller simply redirects everything to index.html, allowing react and react-router to work its magic.
//
//  The RegEx works like this:
//
//        '/' - matches root
//        '/{x:[\\w\\-]+}' - matches everything up to the second \. Eg. \foo
//        '/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}' - matches everything that doesn't start with api. Eg. \foo\bar?page=1
