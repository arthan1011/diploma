package org.arthan.semantic.service.graph.impl;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.VCARD;
import org.arthan.semantic.model.Contact;
import org.arthan.semantic.model.File;
import org.arthan.semantic.service.graph.GraphFileService;
import org.arthan.semantic.service.graph.GraphVCardService;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.Props;
import org.arthan.semantic.service.graph.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Arthur Shamsiev on 27.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */

@Service
public class GraphVCardServiceImpl implements GraphVCardService {

    @Autowired
    GraphRepository graphRepository;
    @Autowired
    GraphFileService graphFileService;

    @Override
    public List<Contact> allContacts() {
        List<Resource> contactResources = graphRepository.findResourcesWithType(ResourceType.CONTACT);
        return contactResources.stream()
                .map(this::contactFromResource)
                .collect(Collectors.toList());
    }

    @Override
    public Contact findContact(String id) {
        Resource resContact = graphRepository.getResource(Contact.URI + id);
        return contactFromResource(resContact);
    }

    private Contact contactFromResource(Resource resContact) {
        Contact newContact = new Contact();
        newContact.setId(Contact.extractID(resContact.getURI()));
        newContact.setFirstName(resContact.getProperty(VCARD.Given).getObject().toString());
        newContact.setLastName(resContact.getProperty(VCARD.Family).getObject().toString());

        StmtIterator emailsIterator = resContact.listProperties(VCARD.EMAIL);
        while (emailsIterator.hasNext()) {
            Statement next = emailsIterator.next();
            newContact.getEmails().add(next.getObject().toString());
        }

        StmtIterator imgIterator = resContact.listProperties(Props.IMAGE);
        while (imgIterator.hasNext()) {
            Statement next = imgIterator.next();
            String imgUri = next.getObject().asResource().getURI();
            File file = File.fromURI(imgUri);
            newContact.getImages().add(file);
        }

        List<File> documentsCreatedByContact = graphFileService.findFilesByCreator(resContact.getURI());
        newContact.getDocuments().addAll(documentsCreatedByContact);

        return newContact;
    }

    // http://artur.lazy-magister.org/resources/contact/17
}
