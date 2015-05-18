package org.arthan.semantic.service.data;

import org.arthan.semantic.model.MailMessage;

import java.util.List;

/**
 * Created by artur.shamsiev on 18.05.2015
 */
public interface MBoxService {
    List<MailMessage> allMessages();

}
