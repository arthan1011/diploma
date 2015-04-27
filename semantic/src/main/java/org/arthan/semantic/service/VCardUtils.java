package org.arthan.semantic.service;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by Arthur Shamsiev on 27.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public class VCardUtils {
    public static String readVCardFile(File vCardFile) {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(new FileInputStream(vCardFile), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public static List<VCard> readVCard(File vCardFile) {
        return Ezvcard.parse(readVCardFile(vCardFile)).all();
    }
}
