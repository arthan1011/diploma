package org.arthan.semantic.web.restful.controller;

import org.arthan.semantic.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String findContact(@PathVariable String id) {
        return contactService.findContact(id);
    }
}
