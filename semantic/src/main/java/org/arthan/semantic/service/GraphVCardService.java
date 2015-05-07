package org.arthan.semantic.service;

import org.arthan.semantic.model.Contact;

import java.util.List;

/**
 * Created by artur.shamsiev on 07.05.2015
 */
public interface GraphVCardService {
    List<Contact> allContacts();

    Contact findContact(String id);
}
