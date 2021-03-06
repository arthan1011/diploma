package org.arthan.semantic.util;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Email;
import org.apache.commons.io.IOUtils;
import org.arthan.semantic.model.Contact;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Arthur Shamsiev on 27.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public class VCardUtils {
    public static String readVCardFile(File vCardFile) {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(new InputStreamReader(new FileInputStream(vCardFile), "UTF-8"), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public static List<Contact> readVCard(File vCardFile) {
        List<VCard> all = Ezvcard.parse(readVCardFile(vCardFile)).all();
        List<Contact> collect = all.stream().map(VCardUtils::createContact).collect(Collectors.toList());
        return collect;
    }

    private static Contact createContact(VCard input) {
        Contact contact = new Contact();
        contact.setFirstName(input.getStructuredName().getGiven());
        contact.setLastName(input.getStructuredName().getFamily());
        List<Email> emails = input.getEmails();
        for (Email email : emails) {
            contact.getEmails().add(email.getValue());
        }
        return contact;
    }

    /**
     * Читает из файла по дефолту
     * @return
     */
    public static List<Contact> readVCard() {
        return readVCard(getDefaultVCardFile());
    }

    static public File getDefaultVCardFile() {
        return new File(System.getProperty("user.home") + FileUtils.prop.getProperty("path.default.vcard"));
    }
}
