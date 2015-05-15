package org.arthan.semantic.web.restful.controller;

import org.arthan.semantic.service.middle.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by artur.shamsiev on 13.05.2015
 */

@RestController
@RequestMapping("/main")
public class MainPageController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public String mainInfo() {
        return userService.findUserInfo();
    }
}
