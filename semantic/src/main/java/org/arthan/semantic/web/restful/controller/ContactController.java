package org.arthan.semantic.web.restful.controller;

import org.arthan.semantic.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ContactService contactService;

    @RequestMapping(value = "/listdata", method = RequestMethod.GET)
    @ResponseBody
    public String listData() {
        return contactService.listAllContacts();
    }
}
