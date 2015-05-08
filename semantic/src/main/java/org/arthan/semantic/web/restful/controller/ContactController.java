package org.arthan.semantic.web.restful.controller;

import org.arthan.semantic.service.ContactService;
import org.arthan.semantic.service.ImageService;
import org.arthan.semantic.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;

/**
 * Created by artur.shamsiev on 22.04.2015.
 */

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    ContactService contactService;
    @Autowired
    ImageService imageService;
    @Autowired
    ServletContext servletContext;

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

    @RequestMapping(value = "/image/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String addImage(
            @RequestParam String imagePath,
            @PathVariable String id) {

        String webDataPath = servletContext.getRealPath("/data");
        String image = FileUtils.cutOffUserHome(imagePath);
        String absWebImagePath = webDataPath + image;

        return imageService.addImage(imagePath, absWebImagePath, id);
    }

}
