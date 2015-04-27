package org.arthan.semantic.service;

import com.google.common.base.Strings;
import ezvcard.VCard;
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

    @Override
    public String listAllContacts() {
        List<VCard> cards = vCardService.findVCards();
        List<String> names = cards.stream().map(input ->
                input.getStructuredName().getGiven() + " " + input.getStructuredName().getFamily()).collect(Collectors.toList());

        // фильтруем от пустых имен
        names = names.stream().filter(input -> !Strings.isNullOrEmpty(input)).collect(Collectors.toList());
        String contacts = new JSONStringer()
            .object()
                .key("contacts")
                .value(names.toArray())
            .endObject()
        .toString();
        System.out.println(contacts);

        return contacts;
    }

}
