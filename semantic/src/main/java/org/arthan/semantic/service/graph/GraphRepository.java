package org.arthan.semantic.service.graph;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import org.arthan.semantic.model.User;
import org.arthan.semantic.service.impl.GraphVCardServiceImpl;
import org.arthan.semantic.util.FileUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Arthur Shamsiev on 29.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GraphRepository {
    private Model model;

    public Model getModel() {
        if (model == null) {
            initModel();
        }
        return model;
    }

    private void initModel() {
        model = ModelFactory.createDefaultModel();
        model.setNsPrefix("arlm", Props.URI);
        if (FileUtils.modelFileExists()) {
            model.read(FileUtils.modelIS(), null);
        }
        setUserIfNotDefined();
    }

    private void setUserIfNotDefined() {
        List<Resource> users = findResourcesWithType(ResourceType.USER.getUri());
        if (users.isEmpty()) {
            addResource(User.URI, ResourceType.USER.getUri());
        }
    }

    public void writeGraph() {
        OutputStream out = FileUtils.modelOS();
        model.write(out);
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Resource addResource(String uri, String typeUri) {
        Resource res = getModel().createResource(uri);
        res.addProperty(RDF.type, typeUri);
        res.addProperty(Props.OWNER, User.URI);
        return res;
    }

    public Resource getResource(String uri) {
        return getModel().getResource(uri);
    }

    public List<Resource> findResourcesWithType(String type) {
        ResIterator contactIterator = getModel().listSubjectsWithProperty(RDF.type, type);
        return Lists.newArrayList(contactIterator);
    }
}
