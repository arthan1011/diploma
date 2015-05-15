package org.arthan.semantic.service.graph.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hp.hpl.jena.rdf.model.Resource;
import org.arthan.semantic.model.MP3File;
import org.arthan.semantic.service.graph.GraphMusicService;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.Props;
import org.arthan.semantic.service.graph.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by artur.shamsiev on 15.05.2015
 */

@Component
public class GraphMusicServiceImpl implements GraphMusicService {

    @Autowired
    GraphRepository graphRep;

    @Override
    public void addMusicToGraph(MP3File mp3File) {
        Preconditions.checkNotNull(
                Strings.emptyToNull(mp3File.getPath()),
                "Не могу добавить музыкальный файл без идентификатора-пути в граф"
        );
        Resource mp3Res = graphRep.addResource(mp3File.getUriID(), ResourceType.MP3FILE.getUri());
        mp3Res.addProperty(Props.MUSIC_ALBUM, mp3File.getAlbum());
        mp3Res.addProperty(Props.PERFORMER, mp3File.getPerformer());
        mp3Res.addProperty(Props.MUSIC_GENRE, mp3File.getGenre());
        mp3Res.addProperty(Props.MUSIC_TITLE, mp3File.getMusicTitle());

        graphRep.writeGraph();
    }

    @Override
    public List<MP3File> allUserMusic() {
        List<Resource> musicResList = graphRep.findResourcesWithType(ResourceType.MP3FILE.getUri());

        return musicResList.stream()
                .map(this::resourceToMP3File).collect(Collectors.toList());
    }

    private MP3File resourceToMP3File(Resource resource) {
        MP3File mp3File = MP3File.fromUri(resource.getURI());
        mp3File.setMusicTitle(resource.getProperty(Props.MUSIC_TITLE).getObject().toString());
        mp3File.setGenre(resource.getProperty(Props.MUSIC_GENRE).getObject().toString());
        mp3File.setPerformer(resource.getProperty(Props.PERFORMER).getObject().toString());
        mp3File.setAlbum(resource.getProperty(Props.MUSIC_ALBUM).getObject().toString());

        return mp3File;
    }
}
