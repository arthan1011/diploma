package org.arthan.semantic.graph;

import com.hp.hpl.jena.rdf.model.Model;

import java.io.File;
import java.util.Properties;

/**
 * Created by Arthur Shamsiev on 27.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public class ModelWrapper {

    private Model model;
    private Properties idProps;

    public ModelWrapper(File graphFile) {

    }

    public ModelWrapper() {

    }

    public long findLastIDFor(ResourceType type) {
        return Long.parseLong(idProps.getProperty(type.name(), "0"));
    }

    public static ModelWrapper initModel() {
        File graphFile = new File(System.getProperty("user.home") + "/.semantic/graph.xml");
        if (graphFile.exists()) {
            return new ModelWrapper(graphFile);
        } else {
            return new ModelWrapper();
        }
    }
}
