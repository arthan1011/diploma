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
    private VCardService vCardService;
    @Autowired
    private GraphVCardService graphVCardService;

    @Override
    public String listAllContacts() {
        List<Contact> contactList = graphVCardService.allContacts();
        List<String> names = contactList.stream().map(input ->
                input.getFirstName()+ " " + input.getLastName()).collect(Collectors.toList());

        // фильтруем от пустых имен
        names = names.stream().filter(input -> !Strings.isNullOrEmpty(input)).collect(Collectors.toList());
        String contactsJSON = new JSONStringer()
            .object()
                .key("contacts")
                .value(contactList)
            .endObject()
        .toString();
        System.out.println(contactsJSON);

        return contactsJSON;
    }

}
