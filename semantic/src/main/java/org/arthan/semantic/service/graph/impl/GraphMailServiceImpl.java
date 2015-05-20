package org.arthan.semantic.service.graph.impl;

import com.google.common.base.Charsets;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import org.apache.commons.lang3.StringEscapeUtils;
import org.arthan.semantic.model.MailMessage;
import org.arthan.semantic.service.data.MBoxService;
import org.arthan.semantic.service.graph.GraphMailService;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.Props;
import org.arthan.semantic.service.graph.ResourceType;
import org.arthan.semantic.util.MBoxUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by artur.shamsiev on 19.05.2015
 */

@Component("graphMBoxService")
public class GraphMailServiceImpl implements GraphMailService {

    @Inject
    MBoxService mBoxService;
    @Inject
    GraphRepository graphRep;

    @Override
    public void addToGraph() {
        List<MailMessage> mailMessages = mBoxService.allMessages();

        deleteAllMessagesFromGraph();
        addMessagesToGraph(mailMessages);

        graphRep.writeGraph();
    }

    @Override
    public List<MailMessage> allMessages() {
        List<Resource> mailResList = graphRep.findResourcesWithType(ResourceType.MAIL);
        return mailResList.stream()
                .map(this::resourceToMessageWithoutBody)
                .collect(Collectors.toList());
    }

    @Override
    public MailMessage findMessage(String mailID) {
        Resource resource = graphRep.getResource(MailMessage.URI + mailID);
        return resourceToMessage(resource);
    }

    private MailMessage resourceToMessageWithoutBody(Resource resource) {
        MailMessage mail = MailMessage.fromURI(resource.getURI());
        mail.setSubject(resource.getProperty(DC.subject).getObject().toString());
        mail.setDateString(resource.getProperty(DC.date).getObject().toString());
        mail.setFrom(resource.getProperty(Props.FROM).getObject().toString());
        return mail;
    }

    private MailMessage resourceToMessage(Resource resource) {
        MailMessage mail = resourceToMessageWithoutBody(resource);
        String encodedBody = resource.getProperty(Props.BODY).getObject().toString();
        mail.setBody(decodeFromBase64(encodedBody));
        return mail;
    }

    private String decodeFromBase64(String encodedBody) {
        return new String(Base64Utils.decodeFromString(encodedBody), Charsets.UTF_8);
    }

    private void addMessagesToGraph(List<MailMessage> mailMessages) {
        mailMessages.stream().forEach(this::addMessageToGraph);
    }

    private void addMessageToGraph(MailMessage mail) {
        Resource mailRes = graphRep.addResource(MailMessage.URI + mail.getId(), ResourceType.MAIL.getUri());
        mailRes.addProperty(DC.subject, mail.getSubject());
        mailRes.addProperty(DC.date, mail.getDateString());
        String body = mail.getBody();
        String encodedBody = encodeStringForXML(body);
        mailRes.addProperty(Props.BODY, encodedBody);
        mailRes.addProperty(Props.FROM, mail.getFrom());
    }

    private String encodeStringForXML(String body) {
        return Base64Utils.encodeToString(body.getBytes());
    }

    private void deleteAllMessagesFromGraph() {
        List<Resource> mailsList = graphRep.findResourcesWithType(ResourceType.MAIL);
        mailsList.stream().forEach(Resource::removeProperties);
    }

}
