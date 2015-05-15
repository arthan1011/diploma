package org.arthan.semantic.service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.VCARD;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import org.arthan.semantic.model.Contact;
import org.arthan.semantic.service.graph.GraphVCardService;
import org.arthan.semantic.service.VCardService;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.ResourceType;
import org.arthan.semantic.util.GraphUtils;
import org.arthan.semantic.util.VCardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.intersection;

/**
 * Created by Arthur Shamsiev on 25.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */

@Component
public class VCardServiceImpl implements VCardService {

    @Autowired
    GraphRepository graphRep;
    @Autowired
    GraphVCardService graphVCardService;

    @Override
    public List<VCard> findVCards() {
        return Ezvcard.parse(getContactsString()).all();
    }

    @Override
    public void addToGraph() {
        List<Contact> contacts = VCardUtils.readVCard();
        List<Contact> storedContacts = graphVCardService.allContacts();
        if (storedContacts.isEmpty()) {
            createContacts(contacts, 0);
        } else {
            long lastContactID = findLastID(storedContacts);
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
        updatedContacts.forEach(this::replace);
    }

    public void replace(Contact contact) {
        SimpleSelector selector = GraphUtils.selectorForType(ResourceType.CONTACT.getUri());
        StmtIterator stmtIterator = graphRep.getModel().listStatements(selector);
        while (stmtIterator.hasNext()) {
            Statement statement = stmtIterator.next();
            String uri = statement.getSubject().getURI();
            if (uri.equals(Contact.URI + contact.getId())) {
                graphRep.getModel().remove(statement);
                break;
            }
        }
        create(contact);
    }

    @VisibleForTesting
    static List<Contact> findUpdatedContacts(List<Contact> storedContacts, List<Contact> contacts) {
        List<Contact> resultList = Lists.newArrayList();
        for (Contact contact : contacts) {
            if (contact.getEmails().isEmpty()) {
                continue;
            }
            Optional<Contact> optUpdateContact = findUpdatedContact(contact, storedContacts);
            if (optUpdateContact.isPresent()) {
                contact.setId(optUpdateContact.get().getId());
                resultList.add(contact);
            }
        }
        return resultList;
    }

    private static Optional<Contact> findUpdatedContact(Contact contact, List<Contact> storedContacts) {
        List<Contact> updatedContacts = storedContacts.stream()
                .filter(saved -> !intersection(saved.getEmails(), contact.getEmails()).isEmpty())
                .filter(saved -> !saved.equals(contact)).collect(Collectors.toList());
        return updatedContacts.stream().findFirst();
    }

    @VisibleForTesting
    static List<Contact> findNewContacts(List<Contact> storedContacts, List<Contact> contacts) {
        List<Contact> resultList = Lists.newArrayList();
        for (Contact contact : contacts) {
            boolean isNew = storedContacts.stream().allMatch(
                    saved ->
                            !saved.equals(contact) &&
                            intersection(saved.getEmails(), contact.getEmails()).isEmpty());
            if (isNew) {
                resultList.add(contact);
            }
        }
        return resultList;
    }

    private long findLastID(List<Contact> contacts) {
        return contacts.stream().mapToLong(Contact::getId).max().orElse(0);
    }

    private void create(Contact contact) {
        Resource res = graphRep.addResource(Contact.URI + contact.getId(),  ResourceType.CONTACT.getUri());
        res.addProperty(VCARD.Given, contact.getFirstName());
        res.addProperty(VCARD.Family, contact.getLastName());
        for (String email : contact.getEmails()) {
            res.addProperty(VCARD.EMAIL, email);
        }
    }

    private void createContacts(List<Contact> contacts, long lastContactID) {
        for (Contact contact : contacts) {
            String uri = Contact.URI + ++lastContactID;
            Resource res = graphRep.addResource(uri, ResourceType.CONTACT.getUri());

            res.addProperty(VCARD.Given, contact.getFirstName());
            res.addProperty(VCARD.Family, contact.getLastName());
            for (String email : contact.getEmails()) {
                res.addProperty(VCARD.EMAIL, email);
            }
        }
    }

    private String getContactsString() {
        File defaultFile = VCardUtils.getDefaultVCardFile();
        return VCardUtils.readVCardFile(defaultFile);
    }

}
