package org.arthan.semantic.model;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arthur Shamsiev on 27.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public class Contact {
    public static final String URI = "http://artur.lazy-magister.org/resources/contact/";

    private long id;
    private String firstName;
    private String lastName;
    private List<String> emails = Lists.newArrayList();
    private List<String> images = Lists.newArrayList();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public static Contact create(Contact contact, long id) {
        Contact result = new Contact();
        result.setEmails(Lists.newArrayList(contact.getEmails()));
        result.setImages(Lists.newArrayList(contact.getImages()));
        result.setFirstName(contact.getFirstName());
        result.setLastName(contact.getLastName());
        result.setId(id);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (emails != null ? !emails.equals(contact.emails) : contact.emails != null) return false;
        if (firstName != null ? !firstName.equals(contact.firstName) : contact.firstName != null) return false;
        if (lastName != null ? !lastName.equals(contact.lastName) : contact.lastName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (emails != null ? emails.hashCode() : 0);
        return result;
    }

    public static long extractID(String uri) {
        return Long.parseLong(uri.substring(URI.length()));
    }
}
