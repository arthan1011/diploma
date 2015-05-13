package org.arthan.semantic.web.restful.controller;

import org.arthan.semantic.service.ContactService;
import org.arthan.semantic.service.FileService;
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
    FileService fileService;
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

    @RequestMapping(value = "/image/{contactID}", method = RequestMethod.POST)
    @ResponseBody
    public String addImage(
            @RequestParam String imagePath,
            @PathVariable String contactID) {

        String webDataPath = servletContext.getRealPath("/data");
        String image = FileUtils.cutOffUserHome(imagePath);
        String absWebImagePath = webDataPath + image;

        return fileService.addImage(imagePath, absWebImagePath, contactID);
    }

    @RequestMapping(value = "/document/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String addDocument(
            @RequestParam String filePath,
            @PathVariable String id) {

        return fileService.addDocument(filePath, id);
    }

}
