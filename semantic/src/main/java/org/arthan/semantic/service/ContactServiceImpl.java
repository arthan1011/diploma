package org.arthan.semantic.service;

import com.google.common.collect.Lists;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import org.apache.commons.io.IOUtils;
import org.json.JSONStringer;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Arthur Shamsiev on 23.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */

@Service("contactService")
public class ContactServiceImpl implements ContactService {
    @Override
    public String listAllContacts() {
        List<VCard> cards = Ezvcard.parse(getContactsString()).all();
        List<String> emails = cards.stream().map(input ->
                input.getEmails().get(0).getValue()).collect(Collectors.toList());

        String contacts = new JSONStringer()
            .object()
                .key("contacts")
                .value(emails.toArray())
            .endObject()
        .toString();

        return contacts;
    }

    private String getContactsString() {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(new FileInputStream(System.getProperty("user.home") + "/.semantic/list.vcf"), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }
}
