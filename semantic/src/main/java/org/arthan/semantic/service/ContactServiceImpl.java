package org.arthan.semantic.service;

import com.google.common.base.Strings;
import org.arthan.semantic.model.Contact;
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
        System.out.println(contactsJSON);

        return contactsJSON;
    }

    @Override
    public String findContact(String id) {
        Contact contact = graphVCardService.findContact(id);
        return new JSONStringer()
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
                .endObject()
            .endObject()
        .toString();
    }

}
