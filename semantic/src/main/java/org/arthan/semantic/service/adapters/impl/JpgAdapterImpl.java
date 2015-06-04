package org.arthan.semantic.service.adapters.impl;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import org.arthan.semantic.model.File;
import org.arthan.semantic.service.adapters.JpgAdapter;
import org.arthan.semantic.service.graph.GraphFileService;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.OntologyRepository;
import org.arthan.semantic.service.graph.Props;
import org.arthan.semantic.util.FileUtils;
import org.arthan.semantic.web.restful.controller.WebController;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.arthan.semantic.util.FileUtils.copyFile;
import static org.arthan.semantic.util.FileUtils.toUnixPath;

/**
 * Created by artur.shamsiev on 03.06.2015
 */

@Component("jpgAdapter")
public class JpgAdapterImpl implements JpgAdapter {

    @Inject
    WebController webController;
    @Inject
    GraphFileService graphFileService;
    @Inject
    GraphRepository graphRep;
    @Inject
    OntologyRepository ontologyRepository;

    @Override
    public void addToGraph(File file, String predicateURI, String objectURI) {
        copyFileToServer(file);

        Resource subject = graphFileService.addFileToGraph(file.getPath());
        subject.addProperty(Props.LABEL, file.getTitle());
        subject.addProperty(DC.title, file.getTitle());

        Resource object = graphRep.getResource(objectURI);

        // создаем связь от субъекта к объекту (прямое свойство)
        subject.addProperty(Props.forUri(predicateURI), object);

        // а теперь создаем связь (обратное свойство) от объекта к субъекту
        object.addProperty(
                ontologyRepository.reversePropFor(Props.forUri(predicateURI)),
                subject);

        graphRep.writeGraph();
    }

    private void copyFileToServer(File file) {
        copyFile(
                file.getAbsPath(),
                toUnixPath(webController.getDataPath()) + file.getPath());
    }
}
