package org.arthan.semantic.service.adapters.impl;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import org.arthan.semantic.model.File;
import org.arthan.semantic.model.MP3File;
import org.arthan.semantic.service.adapters.MP3Adapter;
import org.arthan.semantic.service.data.MP3Service;
import org.arthan.semantic.service.graph.GraphMusicService;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.Props;
import org.arthan.semantic.service.graph.ResourceType;
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
    WebController webController;
    @Inject
    GraphRepository graphRep;
    @Inject
    MP3Service mp3Service;
    @Inject
    GraphMusicService graphMusicService;

    @Override
    public void addToGraph(String filePath, String predicateURI, String objectURI) {
//        copyFileToServer(filePath);

        String filePathFromHome = FileUtils.cutOffUserHome(filePath);
        filePathFromHome = FileUtils.toUnixPath(filePathFromHome);
        String fileName = FileUtils.extractFileName(filePathFromHome);
        MP3File mp3File = mp3Service.findMP3(filePath);

        // добавляем в граф
        Resource subject = graphMusicService.addMusicToGraph(mp3File);
        subject.addProperty(Props.LABEL, fileName);
        subject.addProperty(DC.title, fileName);

        Resource object = graphRep.getResource(objectURI);
        subject.addProperty(Props.forUri(predicateURI), object);

        graphRep.writeGraph();
    }

    private void copyFileToServer(String absSysImagePath) {
        FileUtils.copyFile(absSysImagePath, webController.getDataPath());
    }
}
