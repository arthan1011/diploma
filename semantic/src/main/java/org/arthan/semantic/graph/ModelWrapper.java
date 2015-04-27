package org.arthan.semantic.graph;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.vocabulary.VCARD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        this();
        FileInputStream in = getFileInputStream(graphFile);
        model.read(in, null);
    }

    private FileInputStream getFileInputStream(File graphFile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(graphFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;
    }

    public ModelWrapper() {
        model = ModelFactory.createDefaultModel();
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
