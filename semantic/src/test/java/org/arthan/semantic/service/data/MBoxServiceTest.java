package org.arthan.semantic.service.data;

import org.arthan.semantic.model.MailMessage;
import org.arthan.semantic.service.data.impl.MBoxServiceImpl;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.List;

/**
 * Created by artur.shamsiev on 18.05.2015
 */
public class MBoxServiceTest {
    private final static Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private final static CharsetEncoder ENCODER = UTF8_CHARSET.newEncoder();

    @Test
    public void testMBoxService() throws Exception {
        MBoxService mboxService = new MBoxServiceImpl();
        List<MailMessage> mailMessages = mboxService.allMessages();
    }
}
