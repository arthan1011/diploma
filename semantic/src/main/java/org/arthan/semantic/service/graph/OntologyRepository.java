package org.arthan.semantic.service.graph;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import org.arthan.semantic.util.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by artur.shamsiev on 04.06.2015
 */

@Component
public class OntologyRepository {
    private OntModel ontModel;

    public OntModel getOntModel() {
        if (ontModel == null) {
            ontModel = ModelFactory.createOntologyModel();
            ontModel.read(FileUtils.ontModelIS(), null);
        }
        return ontModel;
    }

    public Property reversePropFor(Property property) {
        OntProperty ontProperty = getOntModel().getOntProperty(property.getURI());
        String reversePropertyUri = ontProperty.getPropertyValue(Props.REVERSE_PROPERTY).asResource().getURI();
        return Props.forUri(reversePropertyUri);
    }
}
