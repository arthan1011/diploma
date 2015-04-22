package org.arthan.semantic.restful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by artur.shamsiev on 22.04.2015.
 */

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @RequestMapping(value = "/listdata", method = RequestMethod.GET)
    @ResponseBody
    public String listData() {
        return "{contact: data}";
    }
}
