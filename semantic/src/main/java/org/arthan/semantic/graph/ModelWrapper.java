package org.arthan.semantic.graph;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;
import org.arthan.semantic.model.Contact;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by Arthur Shamsiev on 27.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public class ModelWrapper {

    private Model model;
    private Properties idProps;

    private ModelWrapper(File graphFile) {
        this();
        FileInputStream in = getFileInputStream(graphFile);
        model.read(in, null);
    }

    // TODO: сделать FileService
    private FileInputStream getFileInputStream(File graphFile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(graphFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;
    }

    private ModelWrapper() {
        model = ModelFactory.createDefaultModel();
    }

    public long findLastIDFor(ResourceType type) {
        return Long.parseLong(idProps.getProperty(type.name(), "0"));
    }

    public static ModelWrapper initModel() {
        File graphFile = getDefaultGraphFile();
        if (graphFile.exists()) {
            return new ModelWrapper(graphFile);
        } else {
            return new ModelWrapper();
        }
    }

    private static File getDefaultGraphFile() {
        return new File(System.getProperty("user.home") + "/semantic/graph.xml");
    }

    public Resource addResource(String resourceURI, String typeURI) {
        Resource resource = model.createResource(resourceURI);
        resource.addProperty(RDF.type, typeURI);
        return resource;
    }

    public void replace(Contact contact) {
        SimpleSelector selector = createSelectorForType(ResourceType.CONTACT.getUri());
        StmtIterator stmtIterator = model.listStatements(selector);
        while (stmtIterator.hasNext()) {
            Statement statement = stmtIterator.next();
            String uri = statement.getResource().getURI();
            if (uri.equals(Contact.URI + contact.getId())) {
                model.remove(statement);
                break;
            }
        }
        create(contact);
    }

    private void create(Contact contact) {
        Resource res = addResource(Contact.URI + contact.getId(), ResourceType.CONTACT.getUri());
        res.addProperty(VCARD.Given, contact.getFirstName());
        res.addProperty(VCARD.Family, contact.getLastName());
        for (String email : contact.getEmails()) {
            res.addProperty(VCARD.EMAIL, email);
        }
    }


    public List<Contact> findAll(ResourceType contact) {
        List<Contact> resultList = Lists.newArrayList();
        SimpleSelector selector = createSelectorForType(ResourceType.CONTACT.getUri());
        StmtIterator stmtIterator = model.listStatements(selector);
        while (stmtIterator.hasNext()) {
            Statement statement = stmtIterator.next();
            Contact newContact = new Contact();
            Resource resource = statement.getSubject();
            newContact.setId(Contact.extractID(resource.getURI()));
            String firstName = resource.getProperty(VCARD.Given).getObject().toString();
            String lastName = resource.getProperty(VCARD.Family).getObject().toString();
            StmtIterator emailsIterator = resource.listProperties(VCARD.EMAIL);
            while (emailsIterator.hasNext()) {
                Statement next = emailsIterator.next();
                newContact.getEmails().add(next.getObject().toString());
            }
            newContact.setFirstName(firstName);
            newContact.setLastName(lastName);
            resultList.add(newContact);
        }
        return resultList;
    }



    private SimpleSelector createSelectorForType(String typeURI) {
        return new SimpleSelector(null, RDF.type, typeURI);
    }

    public void write() {
        File graphFile = getDefaultGraphFile();
        createIfNotExists(graphFile);
        writeModelToFile(graphFile);

    }

    private void writeModelToFile(File graphFile) {
        try {
            FileOutputStream out = new FileOutputStream(graphFile);
            model.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createIfNotExists(File graphFile) {
        if (!graphFile.exists()) {
            try {
                graphFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Model getModel() {
        return model;
    }
}
