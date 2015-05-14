package org.arthan.semantic.web.restful.controller;

import org.arthan.semantic.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by artur.shamsiev on 14.05.2015
 */

@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public String documentInfo(
            @RequestParam("id") String documentID) {
        return fileService.findDocument(documentID);
    }

}
