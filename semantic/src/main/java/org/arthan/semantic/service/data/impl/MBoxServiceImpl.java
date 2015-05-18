package org.arthan.semantic.service.data.impl;

import com.google.common.base.Charsets;
import lib.mboxiterator.MboxIterator;
import org.arthan.semantic.model.MailMessage;
import org.arthan.semantic.service.data.MBoxService;

import java.io.File;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.List;

/**
 * Created by artur.shamsiev on 18.05.2015
 */
public class MBoxServiceImpl implements MBoxService {
    @Override
    public List<MailMessage> allMessages() {
        String path = System.getProperty("user.home") + "/semantic/Mails.mbox";
        File mboxFile = new File(path);

        MboxIterator build = null;
        try {
            build = MboxIterator.fromFile(mboxFile).charset(Charsets.UTF_8).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (CharBuffer buffer : build) {

        }
        return null;
    }
}
