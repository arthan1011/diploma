package org.arthan.semantic.web.restful.controller;

import com.google.common.collect.Lists;
import org.arthan.semantic.service.graph.GraphService;
import org.arthan.semantic.service.middle.FileService;
import org.arthan.semantic.service.middle.SemGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by artur.shamsiev on 28.05.2015
 */

@RestController
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    SemGraphService semGraphService;
    @Autowired
    FileService fileService;

    @RequestMapping(value = "/class/instances", method = RequestMethod.GET)
    @ResponseBody
    public String findInstancesForClasses(
            @RequestParam("classes") String classes) {
        List<String> classesList = Lists.newArrayList(classes.split(","));
        return semGraphService.findInstancesForClasses(classesList);
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ResponseBody
    public String addNewFile(
            @RequestParam String filePath,
            @RequestParam String predicateURI,
            @RequestParam String objectURI) {
        return fileService.addFile(filePath, predicateURI, objectURI);
    }
}
