package org.arthan.semantic.model;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arthur Shamsiev on 27.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public class Contact {
    private String firstName;
    private String lastName;
    private List<String> emails;

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
        return Collections.unmodifiableList(emails);
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
