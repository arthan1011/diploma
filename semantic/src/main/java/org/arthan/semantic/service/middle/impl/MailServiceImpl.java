package org.arthan.semantic.service.middle.impl;

import org.arthan.semantic.model.MailMessage;
import org.arthan.semantic.service.graph.GraphMailService;
import org.arthan.semantic.service.middle.MailService;
import org.json.JSONStringer;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by artur.shamsiev on 20.05.2015
 */
@Service("mailService")
public class MailServiceImpl implements MailService {
    @Inject
    GraphMailService graphMailService;

    @Override
    public String findMail(String mailID) {
        MailMessage mail = graphMailService.findMessage(mailID);
        String jsonMail = new JSONStringer()
            .object()
                .key("mail")
                .object()
                    .key("id")
                    .value(mail.getId())
                    .key("from")
                    .value(mail.getFrom())
                    .key("subject")
                    .value(mail.getSubject())
                    .key("date")
                    .value(mail.getDateString())
                    .key("body")
                    .value(mail.getBody())
                .endObject()
            .endObject()
        .toString();
        return jsonMail;
    }
}
