package org.arthan.semantic.service.graph;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Created by artur.shamsiev on 08.05.2015
 */
public class Props {
    private static Model m = ModelFactory.createDefaultModel();

    public static final String URI = "http://artur.lazy-magister.org/properties#";
    public static final Property IMAGE = m.createProperty(URI + "IMAGE");
    public static final Property OWNER = m.createProperty(URI + "OWNER");
}
