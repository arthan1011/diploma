package org.arthan.semantic.service.graph;

import org.arthan.semantic.model.MP3File;

import java.util.List;

/**
 * Created by artur.shamsiev on 15.05.2015
 */
public interface GraphMusicService {

    void addMusicToGraph(MP3File mp3File);

    List<MP3File> allUserMusic();

    MP3File findMusicByID(String musicID);
}
