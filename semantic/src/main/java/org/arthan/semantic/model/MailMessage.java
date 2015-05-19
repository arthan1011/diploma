package org.arthan.semantic.model;

import com.google.common.collect.Lists;
import org.apache.james.mime4j.dom.address.Mailbox;

import java.util.Date;
import java.util.List;

/**
 * Created by artur.shamsiev on 18.05.2015
 */
public class MailMessage {
    public static final String URI = "http://artur.lazy-magister.org/resources/mail/";

    private long id;
    private String from;
    private String subject;
    private Date date;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
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
}
