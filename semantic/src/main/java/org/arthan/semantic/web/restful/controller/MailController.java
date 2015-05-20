package org.arthan.semantic.web.restful.controller;

import org.arthan.semantic.service.middle.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by artur.shamsiev on 14.05.2015
 */

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    MailService mailService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public String musicInfo(
            @RequestParam("id") String mailID) {

        return mailService.findMail(mailID);
    }
}
