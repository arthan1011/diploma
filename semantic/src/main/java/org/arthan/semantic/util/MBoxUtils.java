package org.arthan.semantic.util;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.james.mime4j.dom.Entity;
import org.apache.james.mime4j.dom.Message;
import org.apache.james.mime4j.dom.Multipart;
import org.apache.james.mime4j.dom.TextBody;
import org.arthan.semantic.model.MailMessage;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

/**
 * Created by artur.shamsiev on 19.05.2015
 */
public class MBoxUtils {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static MailMessage createMailMessage(Message message) {
        MailMessage mailMessage = new MailMessage();
        mailMessage.setFrom(message.getFrom().get(0).toString());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setDateString(MBoxUtils.formatDate(message.getDate()));
        mailMessage.setBody(createBody(message));
        return mailMessage;
    }

    private static String createBody(Message message) {
        String result;
        TextBody body;
        if (message.isMultipart()) {
            body = bodyFromMultipartMessage(message);
        } else {
            body = (TextBody) message.getBody();
        }
        result = getBodyString(body);
        return result;
    }

    private static TextBody bodyFromMultipartMessage(Message message) {
        List<Entity> bodyParts = ((Multipart) message.getBody()).getBodyParts();
        return (TextBody) bodyParts.get(bodyParts.size() - 1).getBody();
    }

    private static String getBodyString(TextBody textBody) {
        ByteArrayOutputStream byos = new ByteArrayOutputStream();
        try {
            textBody.writeTo(byos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byos.toString();
    }

    public static String formatDate(Date date) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return formatter.format(dateTime);
    }

    public static Date parseDate(String dateString) {
        TemporalAccessor accessor = formatter.parse(dateString);
        LocalDateTime dateTime = LocalDateTime.from(accessor);
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
