package org.arthan.semantic.service.data.impl;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.arthan.semantic.model.File;
import org.arthan.semantic.model.MP3File;
import org.arthan.semantic.service.data.MP3Service;

import java.io.IOException;

/**
 * Created by artur.shamsiev on 14.05.2015
 */
public class MP3ServiceImpl implements MP3Service {
    @Override
    public MP3File findMP3(String absMp3Path) {
        Mp3File mp3File;
        try {
            mp3File = new Mp3File(absMp3Path);
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            throw new RuntimeException(e);
        }

        ID3v2 tag = mp3File.getId3v2Tag();

        MP3File file = (MP3File) File.fromAbsPath(absMp3Path);

        file.setTitle(tag.getTitle());
        file.setAlbum(tag.getAlbum());
        file.setPerformer(tag.getArtist());
        file.setGenre(tag.getGenreDescription());

        return file;
    }
}
