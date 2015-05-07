package org.arthan.semantic.web.restful.controller;

import org.arthan.semantic.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by artur.shamsiev on 22.04.2015.
 */

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    ContactService contactService;
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

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ResponseBody
    public String addImage(@RequestParam String imagePath) {
        String dataPath = servletContext.getRealPath("/data");

        if (!imagePath.startsWith(System.getProperty("user.home"))) {
            return null;
        }
        String image = "";
        String appPath = dataPath + image;
        String path = System.getProperty("user.home") + "/" + image;

        FileInputStream fis;
        Path appFile = new File(appPath).toPath();
        try {
            fis = new FileInputStream(path);
            Files.copy(fis, appFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
