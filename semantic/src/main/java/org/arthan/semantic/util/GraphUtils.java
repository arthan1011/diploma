package org.arthan.semantic.util;

import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Created by Arthur Shamsiev on 29.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public class GraphUtils {
    public static SimpleSelector selectorForType(String typeURI) {
        return new SimpleSelector(null, RDF.type, typeURI);
    }
}
