package org.arthan.semantic.service;

import org.springframework.stereotype.Service;

/**
 * Created by Arthur Shamsiev on 23.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */

@Service("contactService")
public class ContactServiceImpl implements ContactService {
    @Override
    public String listAllContacts() {
        return "{\"contacts\": [\"contact_1\", \"contact_2\", \"contact_3\"]}";
    }
}
