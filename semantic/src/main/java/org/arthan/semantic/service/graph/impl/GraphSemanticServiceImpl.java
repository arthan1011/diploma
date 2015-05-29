package org.arthan.semantic.service.graph.impl;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import org.arthan.semantic.model.Res;
import org.arthan.semantic.model.Triplet;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.GraphSemanticService;
import org.arthan.semantic.service.graph.Props;
import org.arthan.semantic.service.graph.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by artur.shamsiev on 28.05.2015
 */

@Component
public class GraphSemanticServiceImpl implements GraphSemanticService {

    @Autowired
    GraphRepository graphRep;

    @Override
    public List<Res> allResourcesForTypes(List<String> classesList) {
        List<Res> resultList = classesList.stream()
                .flatMap(typeUri -> graphRep.findResourcesWithType(typeUri).stream())
                .map(Res::new)
                .collect(Collectors.toList());

        return resultList;
    }

    @Override
    public void addNewResourceTriplet(Triplet triplet) {
        Resource subject = graphRep.addResource(triplet.getSubject().getUri(), ResourceType.FILE.getUri());
        subject.addProperty(Props.LABEL, triplet.getSubject().getLabel());
        subject.addProperty(DC.title, triplet.getSubject().getLabel());
        Resource object = graphRep.getResource(triplet.getObject().getUri());
        subject.addProperty(Props.forUri(triplet.getPredicate().getUri()), object);

        graphRep.writeGraph();
    }
}
