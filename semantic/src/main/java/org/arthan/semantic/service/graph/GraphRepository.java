package org.arthan.semantic.service.graph;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.RDF;
import org.arthan.semantic.model.User;
import org.arthan.semantic.util.FileUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        OutputStreamWriter out = FileUtils.modelOS();
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

    /**
     * Метод {@link com.hp.hpl.jena.rdf.model.Model#getResource(String uri)} работает аналогично
     * методу {@link com.hp.hpl.jena.rdf.model.Model#createResource(String uri)} и создает новый ресурс в
     * графе, если не находит существующий. Такое поведение нас не устраивает, так как нам необходимо возвращать
     * только существующий ресурс по URI и возвращать null если не существует такого ресурса.
     * @param uri идентификатор существующего ресурса
     * @return возвращаает ресурс, найденный в графе по URI, или null если ресурса с таким URI в графе
     * не существует.
     */
    public Resource getResource(String uri) {
        Resource resource = getModel().createResource(uri);
        boolean isExistingResource = resource.listProperties().hasNext();
        if (isExistingResource) {
            return resource;
        } else {
            return null;
        }
    }

    private List<Resource> findResourcesWithType(String type) {
        ResIterator contactIterator = getModel().listSubjectsWithProperty(RDF.type, type);
        return Lists.newArrayList(contactIterator);
    }

    public List<Resource> findResourcesWithType(ResourceType type) {
        return findResourcesWithType(type.getUri());
    }

    public List<Resource> subjectsFromSelector(SimpleSelector findDoc) {
        StmtIterator docsIterator = getModel().listStatements(findDoc);
        ArrayList<Statement> docStatements = Lists.newArrayList(docsIterator);
        return docStatements.stream()
                .map(Statement::getSubject).collect(Collectors.toList());
    }
}
