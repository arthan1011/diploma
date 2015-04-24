package org.arthan.semantic.service;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static org.junit.Assert.*;

public class ContactServiceImplTest {

    @Test
    public void testFile() throws Exception {
        String contacts = getContactsString();

        List<VCard> cards = Ezvcard.parse(contacts).all();
        for (VCard card : cards) {
            System.out.println(card.getEmails().get(0).getValue());
        }
    }

    private String getContactsString() throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(new FileInputStream(System.getProperty("user.home") + "/.semantic/list.vcf"), writer);
        return writer.toString();
    }
}