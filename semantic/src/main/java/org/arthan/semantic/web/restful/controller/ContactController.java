package org.arthan.semantic.web.restful.controller;

import org.arthan.semantic.service.ContactService;
import org.arthan.semantic.service.ImageService;
import org.arthan.semantic.util.FileUtils;
import org.json.JSONStringer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.File;

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
        String dataPath = servletContext.getRealPath("/data");

        if (!imagePath.startsWith(FileUtils.USER_HOME)) {
            // added file should always be in user home directory
            return new JSONStringer()
                .object()
                    .key("answer")
                    .object()
                        .key("status")
                        .value("not-user")
                    .endObject()
                .endObject()
            .toString();
        }

        String image = extractRelativeImagePath(imagePath);
        String webImagePath = dataPath + image;
        File webImage = new File(webImagePath);

        // если файла не существует, копируем его на сервер
        if (!webImage.exists()) {
            FileUtils.copyFile(image, webImagePath);
        }

        imageService.addImageToGraphForContact(image, id);

        return null;
    }

    private String extractRelativeImagePath(String imagePath) {
        return imagePath.split(FileUtils.USER_HOME)[1];
    }
}
