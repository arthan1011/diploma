package org.arthan.semantic.service.data;

import com.google.common.base.Charsets;
import lib.mboxiterator.MboxIterator;
import org.apache.james.mime4j.dom.Body;
import org.apache.james.mime4j.dom.Message;
import org.apache.james.mime4j.dom.Multipart;
import org.apache.james.mime4j.dom.TextBody;
import org.apache.james.mime4j.storage.StorageOutputStream;
import org.apache.james.mime4j.message.DefaultMessageBuilder;
import org.arthan.semantic.model.MailMessage;
import org.arthan.semantic.service.data.impl.MBoxServiceImpl;
import org.junit.Assume;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.List;

/**
 * Created by artur.shamsiev on 18.05.2015
 */
public class MBoxServiceTest {
    private final static Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private final static CharsetEncoder ENCODER = UTF8_CHARSET.newEncoder();

    @Test
    public void testMBox() throws Exception {

        String path = System.getProperty("user.home") + "/semantic/Mails2.mbox";
        File mboxFile = new File(path);

        Assume.assumeTrue(mboxFile.exists());

        MBoxService mboxService = new MBoxServiceImpl();

        MboxIterator build = MboxIterator.fromFile(mboxFile).charset(Charsets.UTF_8).build();
        for (CharBuffer buffer : build) {
            ByteBuffer byteBuffer = ENCODER.encode(buffer);
            DefaultMessageBuilder messageBuilder = new DefaultMessageBuilder();
            ByteArrayInputStream bis = new ByteArrayInputStream(byteBuffer.array());
            Message message = messageBuilder.parseMessage(bis);
            if (!message.isMultipart()) {
                TextBody body = (TextBody) message.getBody();
                body.writeTo(System.out);
            }
            System.out.println(String.format(
                    "From: %s\nTo: %s\nDate: %s\n",
                    message.getFrom(),
                    message.getSubject(),
                    message.getDate()
            ));
        }
    }

}
