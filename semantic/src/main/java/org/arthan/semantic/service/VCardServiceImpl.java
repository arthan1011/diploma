package org.arthan.semantic.service;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Created by Arthur Shamsiev on 25.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */

@Component
public class VCardServiceImpl implements VCardService {

    @Override
    public List<VCard> findVCards() {
        return Ezvcard.parse(getContactsString()).all();
    }

    private String getContactsString() {
        File defaultFile = VCardUtils.getDefaultVCardFile();
        return VCardUtils.readVCardFile(defaultFile);
    }

}
