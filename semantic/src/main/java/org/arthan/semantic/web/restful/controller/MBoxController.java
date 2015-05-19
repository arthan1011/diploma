package org.arthan.semantic.web.restful.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by artur.shamsiev on 19.05.2015
 */

@RestController
@RequestMapping("/mails")
public class MBoxController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "<html>" +
                "   <head>" +
                "   </head>" +
                "   <body>" +
                "       <style>" +
                "           h1 {font-size: 18px !important;}" +
                "       </style>" +
                "       <h1>Text</h1>" +
                "   </body>" +
                "</html>";
    }

}
