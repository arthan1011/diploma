package org.arthan.semantic.model;

import com.hp.hpl.jena.rdf.model.Resource;
import org.arthan.semantic.service.graph.Props;

/**
 * Created by artur.shamsiev on 28.05.2015
 */
public class Res {
    private String label;
    private String uri;

    public Res(String label, String uri) {
        this.label = label;
        this.uri = uri;
    }

    public Res(Resource graphResource) {
        this(
            graphResource.getProperty(Props.LABEL).getObject().toString(),
            graphResource.getURI()
        );
    }

    public String getLabel() {
        return label;
    }

    public String getUri() {
        return uri;
    }
}
