package org.arthan.semantic.service.middle.impl;

import org.arthan.semantic.model.Contact;
import org.arthan.semantic.service.middle.ContactService;
import org.arthan.semantic.service.graph.GraphVCardService;
import org.json.JSONStringer;
import org.json.JSONWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Arthur Shamsiev on 23.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */

@Service("contactService")
public class ContactServiceImpl implements ContactService {

    @Autowired
    private GraphVCardService graphVCardService;

    @Override
    public String listAllContacts() {
        List<Contact> contactList = graphVCardService.allContacts();
        String contactsJSON = new JSONStringer()
            .object()
                .key("contacts")
                .value(contactList)
            .endObject()
        .toString();

        return contactsJSON;
    }

    @Override
    public String findContact(String id) {
        Contact contact = graphVCardService.findContact(id);
        String jsonContact = new JSONStringer()
            .object()
                .key("contact")
                .object()
                    .key("id")
                    .value(contact.getId())
                    .key("firstName")
                    .value(contact.getFirstName())
                    .key("lastName")
                    .value(contact.getLastName())
                    .key("emails")
                    .value(contact.getEmails())
                    .key("images")
                    .value(contact.getImages())
                    .key("documents")
                    .value(contact.getDocuments())
                .endObject()
            .endObject()
        .toString();
        return jsonContact;
    }

    @Override
    public String allContacts() {
        List<Contact> contactList = graphVCardService.allContacts();
        JSONWriter contacts = new JSONStringer()
                .object()
                .key("contacts")
                .array();
        for (Contact contact : contactList) {
            contacts = contacts
            .object()
                .key("uri")
                .value(Contact.URI + contact.getId())
                .key("label")
                .value(contact.getFirstName() + " " + contact.getLastName())
            .endObject();
        }
        String jsonContacts = contacts.endArray().endObject().toString();
        return jsonContacts;
    }

}
