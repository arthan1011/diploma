package org.arthan.semantic.service.graph;

import org.arthan.semantic.model.MailMessage;

import java.util.List;

/**
 * Created by artur.shamsiev on 19.05.2015
 */
public interface GraphMailService {
    void addToGraph();

    List<MailMessage> allMessages();

    MailMessage findMessage(String mailID);
}
