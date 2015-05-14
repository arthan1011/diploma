package org.arthan.semantic.service.impl;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.DC;
import org.arthan.semantic.model.Contact;
import org.arthan.semantic.model.File;
import org.arthan.semantic.service.GraphFileService;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.Props;
import org.arthan.semantic.service.graph.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by artur.shamsiev on 08.05.2015
 */

@Component
public class GraphFileServiceImpl implements GraphFileService {

    @Autowired
    GraphRepository graphRepository;

    @Override
    public void addImageToGraphForContact(String filePath, String contactID, String imageName) {
        Resource fileRes = graphRepository.addResource(File.URI + filePath, ResourceType.FILE.getUri());
        Resource contactRes = graphRepository.getResource(Contact.URI + contactID);
        contactRes.addProperty(Props.IMAGE, fileRes);
        fileRes.addProperty(DC.title, imageName);

        graphRepository.writeGraph();
    }

    @Override
    public void addDocumentToGraphForContact(String documentPath, String documentName, String contactID) {
        Resource fileRes = graphRepository.addResource(File.URI + documentPath, ResourceType.FILE.getUri());
        Resource contactRes = graphRepository.getResource(Contact.URI + contactID);
        fileRes.addProperty(DC.creator, contactRes);
        fileRes.addProperty(DC.title, documentName);

        graphRepository.writeGraph();
    }

    @Override
    public List<File> findFilesByCreator(String creatorUri) {
        List<File> resultList;
        Model model = graphRepository.getModel();
        SimpleSelector findDoc = new SimpleSelector(null, DC.creator, model.getResource(creatorUri));
        List<Resource> docResList = graphRepository.subjectsFromSelector(findDoc);

        resultList = docResList.stream()
                .map(this::fileFromResource)
                .collect(Collectors.toList());

        return resultList;
    }

    @Override
    public List<File> allUserDocuments() {
        List<File> resultList;
        List<Resource> docResList = graphRepository.findResourcesWithType(ResourceType.FILE.getUri());
        resultList = docResList.stream()
                .map(this::fileFromResource)
                .collect(Collectors.toList());
        return resultList;
    }

    private File fileFromResource(Resource fileResource) {
        File doc = File.fromURI(fileResource.getURI());
        doc.setTitle(fileResource.getProperty(DC.title).getObject().toString());
        return doc;
    }
}
