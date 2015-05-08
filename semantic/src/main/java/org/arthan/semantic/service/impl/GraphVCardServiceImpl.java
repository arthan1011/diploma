package org.arthan.semantic.service.impl;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;
import org.arthan.semantic.model.Contact;
import org.arthan.semantic.model.File;
import org.arthan.semantic.service.GraphVCardService;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.Props;
import org.arthan.semantic.service.graph.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Arthur Shamsiev on 27.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */

@Service
public class GraphVCardServiceImpl implements GraphVCardService {

    @Autowired
    GraphRepository graphRepository;

    @Override
    public List<Contact> allContacts() {
        List<Contact> resultList = Lists.newArrayList();
        Model model = graphRepository.getModel();
        ResIterator contactIterator = model.listSubjectsWithProperty(RDF.type, ResourceType.CONTACT.getUri());
        while (contactIterator.hasNext()) {
            Resource resContact = contactIterator.next();
            Contact newContact = contactFromResource(resContact);
            resultList.add(newContact);
        }
        return resultList;
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
        return newContact;
    }

    // http://artur.lazy-magister.org/resources/contact/17
}
