package org.arthan.semantic.service.impl;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.VCARD;
import org.arthan.semantic.model.Contact;
import org.arthan.semantic.model.File;
import org.arthan.semantic.service.GraphFileService;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.Props;
import org.arthan.semantic.service.graph.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by artur.shamsiev on 08.05.2015
 */

@Component
public class GraphFileServiceImpl implements GraphFileService {

    @Autowired
    GraphRepository graphRepository;

    @Override
    public void addImageToGraphForContact(String filePath, String contactID) {
        Resource fileRes = graphRepository.addResource(File.URI + filePath, ResourceType.FILE.getUri());
        Resource contactRes = graphRepository.getResource(Contact.URI + contactID);
        contactRes.addProperty(Props.IMAGE, fileRes);

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
        List<File> resultList = Lists.newArrayList();
        Model model = graphRepository.getModel();
        SimpleSelector findDoc = new SimpleSelector(null, DC.creator, model.getResource(creatorUri));
        StmtIterator docsIterator = model.listStatements(findDoc);
        while (docsIterator.hasNext()) {
            Statement next = docsIterator.next();
            Resource document = next.getSubject();
            File doc = File.fromURI(document.getURI());
            doc.setTitle(document.getProperty(DC.title).getObject().toString());
            resultList.add(doc);
        }
        return resultList;
    }
}
