package org.arthan.semantic.service.adapters.impl;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import org.arthan.semantic.model.File;
import org.arthan.semantic.model.MP3File;
import org.arthan.semantic.service.adapters.AdapterComponent;
import org.arthan.semantic.service.adapters.MP3Adapter;
import org.arthan.semantic.service.data.MP3Service;
import org.arthan.semantic.service.graph.*;
import org.arthan.semantic.util.FileUtils;
import org.arthan.semantic.web.restful.controller.WebController;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by artur.shamsiev on 03.06.2015
 */

@Component("mp3Adapter")
public class MP3AdapterImpl implements MP3Adapter {

    @Inject
    MP3Service mp3Service;
    @Inject
    GraphMusicService graphMusicService;
    @Inject
    AdapterComponent adapterComponent;

    @Override
    public void addToGraph(File file, String predicateURI, String objectURI) {

        MP3File mp3File = mp3Service.findMP3(file.getAbsPath());

        // добавляем в граф
        Resource subject = graphMusicService.addMusicToGraph(mp3File);
        adapterComponent.addResource(subject, predicateURI, objectURI, file.getTitle());
    }
}
