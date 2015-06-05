package org.arthan.semantic.service.adapters;

import com.hp.hpl.jena.rdf.model.Resource;
import org.arthan.semantic.model.File;
import org.arthan.semantic.service.graph.GraphFileService;
import org.arthan.semantic.web.restful.controller.WebController;

import javax.inject.Inject;

import static org.arthan.semantic.util.FileUtils.copyFile;
import static org.arthan.semantic.util.FileUtils.toUnixPath;

/**
 * Created by artur.shamsiev on 05.06.2015
 */
public class PictureAdapter implements ResourceAdapter {
    @Inject
    WebController webController;
    @Inject
    GraphFileService graphFileService;
    @Inject
    AdapterComponent adapterComponent;

    @Override
    public void addToGraph(File file, String predicateURI, String objectURI) {
        copyFileToServer(file);

        Resource subject = graphFileService.addFileToGraph(file.getPath());
        adapterComponent.addResource(subject, predicateURI, objectURI, file.getTitle());
    }

    private void copyFileToServer(File file) {
        copyFile(
                file.getAbsPath(),
                toUnixPath(webController.getDataPath()) + file.getPath());
    }
}
