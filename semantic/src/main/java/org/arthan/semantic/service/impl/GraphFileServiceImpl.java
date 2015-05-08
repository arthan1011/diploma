package org.arthan.semantic.service.impl;

import com.hp.hpl.jena.rdf.model.Resource;
import org.arthan.semantic.model.Contact;
import org.arthan.semantic.model.File;
import org.arthan.semantic.service.GraphFileService;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.Props;
import org.arthan.semantic.service.graph.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by artur.shamsiev on 08.05.2015
 */

@Component
public class GraphFileServiceImpl implements GraphFileService {

    @Autowired
    GraphRepository graphRepository;

    @Override
    public void addFileToGraphForContact(String filePath, String contactID) {
        Resource fileRes = graphRepository.addResource(File.URI + filePath, ResourceType.FILE.getUri());
        Resource contactRes = graphRepository.getResource(Contact.URI + contactID);
        contactRes.addProperty(Props.IMAGE, fileRes);

        graphRepository.writeGraph();
    }
}
