package org.arthan.semantic.service.impl;

import com.google.common.base.Strings;
import org.arthan.semantic.model.Contact;
import org.arthan.semantic.service.ContactService;
import org.arthan.semantic.service.GraphVCardService;
import org.json.JSONStringer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

}
