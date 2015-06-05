package org.arthan.semantic.service.adapters;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.OntologyRepository;
import org.arthan.semantic.service.graph.Props;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by artur.shamsiev on 05.06.2015
 */

@Component
public class AdapterComponent {

    @Inject
    OntologyRepository ontologyRepository;
    @Inject
    GraphRepository graphRep;

    public void addResource(Resource subject, String predicateURI, String objectURI, String title) {
        subject.addProperty(Props.LABEL, title);
        subject.addProperty(DC.title, title);

        Resource object = graphRep.getResource(objectURI);
        subject.addProperty(Props.forUri(predicateURI), object);
        object.addProperty(
                ontologyRepository.reversePropFor(Props.forUri(predicateURI)),
                subject);

        graphRep.writeGraph();
    }
}
