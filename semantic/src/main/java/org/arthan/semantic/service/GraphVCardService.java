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
            long lastContactID = modelWrapper.findLastIDFor(ResourceType.CONTACT);
            addContacts(storedContacts, contacts, lastContactID);
        }
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
            resultList.addAll(storedContacts.stream().filter(
                    savedContact -> savedContact.getEmails().contains(contact.getEmails())).map(
                    savedContact -> Contact.create(contact, savedContact.getId())).collect(Collectors.toList()));
        }
        return resultList;
    }

    private List<Contact> findNewContacts(List<Contact> storedContacts, List<Contact> contacts) {
        List<Contact> resultList = Lists.newArrayList();
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact =  storedContacts.get(i);
            resultList.addAll(storedContacts.stream().filter(
                    savedContact -> !savedContact.equals(contact) && !savedContact.getEmails().contains(contact.getEmails())).map(
                    savedContact -> contact).collect(Collectors.toList()));
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
