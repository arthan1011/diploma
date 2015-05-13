package org.arthan.semantic.service.impl;

import org.arthan.semantic.model.Contact;
import org.arthan.semantic.service.GraphVCardService;
import org.arthan.semantic.service.UserService;
import org.json.JSONStringer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by artur.shamsiev on 13.05.2015
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private GraphVCardService graphVCardService;

    @Override
    public String findUserInfo() {
        List<Contact> contactList = graphVCardService.allContacts();
        String userInfoJson = new JSONStringer()
            .object()
                .key("main")
                .object()
                    .key("contacts")
                    .value(contactList)
                .endObject()
            .endObject()
        .toString();
        return userInfoJson;
    }
}
