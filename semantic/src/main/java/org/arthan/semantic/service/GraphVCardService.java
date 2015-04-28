package org.arthan.semantic.service;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;
import org.arthan.semantic.graph.ModelWrapper;
import org.arthan.semantic.graph.ResourceType;
import org.arthan.semantic.model.Contact;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Arthur Shamsiev on 27.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */

@Service
public class GraphVCardService {

    private ModelWrapper modelWr;

    public void addToModel(File vCardFile, ModelWrapper modelWrapper) {
        List<Contact> contacts = VCardUtils.readVCard(vCardFile);

        modelWr = modelWrapper;
        List<Contact> storedContacts = modelWr.findAll(ResourceType.CONTACT);

        if (storedContacts.isEmpty()) {
            createContacts(contacts, 0);
        } else {
            long lastContactID = findLastID(storedContacts);
            addContacts(storedContacts, contacts, lastContactID);
        }
    }

    private long findLastID(List<Contact> contacts) {
        return contacts.stream().mapToLong(input -> input.getId()).max().orElse(0);
    }

    private void addContacts(List<Contact> storedContacts,
                             List<Contact> contacts,
                             long lastContactID) {
        List<Contact> newContacts = findNewContacts(storedContacts, contacts);
        List<Contact> updatedContacts = findUpdatedContacts(storedContacts, contacts);

        updateContacts(updatedContacts);
        createContacts(newContacts, lastContactID);
    }

    private void updateContacts(List<Contact> updatedContacts) {
        for (Contact contact : updatedContacts) {
            modelWr.replace(contact);
        }
    }

    private List<Contact> findUpdatedContacts(List<Contact> storedContacts, List<Contact> contacts) {
        List<Contact> resultList = Lists.newArrayList();
        for (Contact contact : contacts) {
            if (contact.getEmails().isEmpty()) {
                continue;
            }
            boolean isUpdate = storedContacts.stream().anyMatch(
                    saved -> !saved.equals(contact) && saved.getEmails().containsAll(contact.getEmails()));
            if (isUpdate) {
                resultList.add(contact);
            }
        }
        return resultList;
    }

    private List<Contact> findNewContacts(List<Contact> storedContacts, List<Contact> contacts) {
        List<Contact> resultList = Lists.newArrayList();
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact =  storedContacts.get(i);
            boolean isNew = storedContacts.stream().allMatch(
                    saved -> !saved.equals(contact) && !saved.getEmails().containsAll(contact.getEmails()));
            if (isNew) {
                resultList.add(contact);
            }
        }
        return resultList;
    }

    private void createContacts(List<Contact> contacts, long lastContactID) {
        for (Contact contact : contacts) {
            Resource res = modelWr.addResource(Contact.URI + ++lastContactID, ResourceType.CONTACT.getUri());
            res.addProperty(VCARD.Given, contact.getFirstName());
            res.addProperty(VCARD.Family, contact.getLastName());
            for (String email : contact.getEmails()) {
                res.addProperty(VCARD.EMAIL, email);
            }
        }
    }

    // http://artur.lazy-magister.org/resources/contact/17
}
