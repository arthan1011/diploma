package org.arthan.semantic.model;

import java.util.Date;

/**
 * Created by artur.shamsiev on 18.05.2015
 */
public class MailMessage {
    public static final String URI = "http://artur.lazy-magister.org/resources/mail/";

    private long id;
    private String from;
    private String subject;
    private String dateString;
    private String body;

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setDateString(String date) {
        this.dateString = date;
    }

    public String getDateString() {
        return dateString;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public static MailMessage fromURI(String uri) {
        long id = Long.parseLong(uri.substring(URI.length()));
        MailMessage mailMessage = new MailMessage();
        mailMessage.id = id;
        return mailMessage;
    }
}
