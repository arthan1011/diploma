package org.arthan.semantic.web.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;

/**
 * Created by artur.shamsiev on 03.06.2015
 */

@RestController
@Component
public class WebController {

    @Autowired
    ServletContext servletContext;

    public String getDataPath() {
        return servletContext.getRealPath("/data");
    }
}
