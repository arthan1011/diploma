package org.arthan.semantic.service.graph.impl;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.DC;
import org.arthan.semantic.model.MailMessage;
import org.arthan.semantic.service.data.MBoxService;
import org.arthan.semantic.service.graph.GraphMBoxService;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.Props;
import org.arthan.semantic.service.graph.ResourceType;
import org.arthan.semantic.util.GraphUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by artur.shamsiev on 19.05.2015
 */

@Component("graphMBoxService")
public class GraphMBoxServiceImpl implements GraphMBoxService {

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

    private void addMessagesToGraph(List<MailMessage> mailMessages) {
        mailMessages.stream().forEach(this::addMessageToGraph);
    }

    private void addMessageToGraph(MailMessage mail) {
        Resource mailRes = graphRep.addResource(MailMessage.URI + mail.getId(), ResourceType.MAIL.getUri());
        mailRes.addProperty(DC.subject, mail.getSubject());
        mailRes.addProperty(DC.date, mail.getDate().toString());
//        mailRes.addProperty(Props.BODY, mail.getBody());
        mailRes.addProperty(Props.FROM, mail.getFrom());
    }

    private void deleteAllMessagesFromGraph() {
        SimpleSelector mailSelector = GraphUtils.selectorForType(ResourceType.MAIL.getUri());
        StmtIterator mailIterator = graphRep.getModel().listStatements(mailSelector);
        List<Statement> mailsList = Lists.newArrayList(mailIterator);
        mailsList.stream().forEach(Statement::remove);
    }
}
