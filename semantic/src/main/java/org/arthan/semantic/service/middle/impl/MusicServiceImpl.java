package org.arthan.semantic.service.middle.impl;

import org.arthan.semantic.model.MP3File;
import org.arthan.semantic.service.data.MP3Service;
import org.arthan.semantic.service.graph.GraphMusicService;
import org.arthan.semantic.service.middle.MusicService;
import org.arthan.semantic.util.FileUtils;
import org.arthan.semantic.util.JsonAnswerUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by artur.shamsiev on 14.05.2015
 */

@Service("musicService")
public class MusicServiceImpl implements MusicService {

    @Inject
    GraphMusicService graphMusicService;
    @Inject
    MP3Service mp3Service;

    @Override
    public String addMusicFile(String absMusicPath) {

        if (!FileUtils.inHomeDirectory(absMusicPath)) {
            return JsonAnswerUtils.notInHomeAnswer();
        }

        MP3File mp3File = mp3Service.findMP3(absMusicPath);
        graphMusicService.addMusicToGraph(mp3File);

        return JsonAnswerUtils.fileAddedAnswer();
    }
}
