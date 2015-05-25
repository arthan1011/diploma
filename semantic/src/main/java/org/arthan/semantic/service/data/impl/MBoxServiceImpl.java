package org.arthan.semantic.service.data.impl;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import org.apache.james.mime4j.dom.Message;
import org.apache.james.mime4j.mboxiterator.CharBufferWrapper;
import org.apache.james.mime4j.mboxiterator.MboxIterator;
import org.apache.james.mime4j.message.DefaultMessageBuilder;
import org.arthan.semantic.model.MailMessage;
import org.arthan.semantic.service.data.MBoxService;
import org.arthan.semantic.util.FileUtils;
import org.arthan.semantic.util.MBoxUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.List;

/**
 * Created by artur.shamsiev on 18.05.2015
 */

@Service
public class MBoxServiceImpl implements MBoxService {
    private final static Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private final static CharsetEncoder ENCODER = UTF8_CHARSET.newEncoder();

    @Override
    public List<MailMessage> allMessages() {
        List<MailMessage> resultList = Lists.newArrayList();

        String path = System.getProperty("user.home") + FileUtils.prop.getProperty("path.default.mbox");
        File mboxFile = new File(path);

        if (!mboxFile.exists()) {
            throw new RuntimeException("Can't find mbox file with path: " + path);
        }

        try {
            MboxIterator build = MboxIterator.fromFile(mboxFile).charset(Charsets.UTF_8).build();
            long i = 1;
            for (CharBufferWrapper buffer : build) {
                DefaultMessageBuilder messageBuilder = new DefaultMessageBuilder();
                Message message = messageBuilder.parseMessage(buffer.asInputStream(Charsets.UTF_8));

                MailMessage mailMessage = MBoxUtils.createMailMessage(message);
                mailMessage.setId(i++);
                resultList.add(mailMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

}
