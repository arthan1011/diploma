package org.arthan.semantic.web.restful.controller;

import org.arthan.semantic.service.middle.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by artur.shamsiev on 14.05.2015
 */

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    MusicService musicService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addMusicFile(
            @RequestParam("id") String absMusicPath) {
        return musicService.addMusicFile(absMusicPath);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public String musicInfo(
            @RequestParam("id") String musicID) {

        return musicService.findMusic(musicID);
    }
}
