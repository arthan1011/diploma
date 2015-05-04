package org.arthan.semantic.service;

import com.google.common.collect.Lists;
import org.arthan.semantic.model.Contact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class VCardServiceImplTest {

    private List<Contact> contacts;
    private List<Contact> savedContacts;

    @Before
    public void setUp() throws Exception {
        savedContacts = Lists.newArrayList();

        Contact first = new Contact();
        savedContacts.add(first);
        first.setFirstName("First");
        first.setLastName("FCon");
        first.getEmails().addAll(Lists.newArrayList("first1@gmail.com", "first2@gmail.com"));

        Contact second = new Contact();
        savedContacts.add(second);
        second.setFirstName("Second");
        second.setLastName("SCon");
        second.getEmails().addAll(Lists.newArrayList("second0@gmail.com", "second9@gmail.com"));

        contacts = Lists.newArrayList(savedContacts);
    }

    @Test
    public void shouldFindNewContacts() throws Exception {
        Contact nthird = new Contact();
        contacts.add(nthird);
        nthird.setFirstName("Third");
        nthird.setLastName("TCon");
        nthird.getEmails().addAll(Lists.newArrayList("third3@gmail.com", "third33@gmail.com"));

        List<Contact> newContacts = VCardServiceImpl.findNewContacts(savedContacts, contacts);

        Assert.assertEquals("Should find only one new contact", 1, newContacts.size());
        Assert.assertEquals("Should find new Contact", nthird, newContacts.stream().findFirst().get());
    }

    @Test
    public void shouldNotFindNewContactWithAddEmail() throws Exception {
        Contact contact = Contact.create(savedContacts.stream().findFirst().get(), 32);
        contact.getEmails().add("new-email@gmail.com");
        contacts.add(contact);

        List<Contact> newContacts = VCardServiceImpl.findNewContacts(savedContacts, contacts);

        Assert.assertTrue("Should not find any new contacts", newContacts.isEmpty());
    }

    @Test
    public void shouldNotFindNewContactWithRemoveEmail() throws Exception {
        Contact contact = Contact.create(savedContacts.stream().findFirst().get(), 32);
        contact.getEmails().remove(0);
        contacts.add(contact);

        List<Contact> newContacts = VCardServiceImpl.findNewContacts(savedContacts, contacts);

        Assert.assertTrue("Should not find any new contacts", newContacts.isEmpty());
    }

    @Test
    public void shouldFindUpdatedContactWithAddEmail() throws Exception {
        Contact contact = Contact.create(savedContacts.stream().findFirst().get(), 32);
        contact.getEmails().add("new-email@gmail.com");
        contacts.add(contact);

        List<Contact> updatedContacts = VCardServiceImpl.findUpdatedContacts(savedContacts, contacts);

        Assert.assertEquals("Should find only one updated contact", 1, updatedContacts.size());
        Assert.assertEquals("Should find updated contact", contact, updatedContacts.stream().findFirst().get());
    }

    @Test
    public void shouldFindUpdatedContactWithRemoveEmail() throws Exception {
        Contact contact = Contact.create(savedContacts.stream().findFirst().get(), 32);
        contact.getEmails().remove(0);
        contacts.add(contact);

        List<Contact> updatedContacts = VCardServiceImpl.findUpdatedContacts(savedContacts, contacts);

        Assert.assertEquals("Should find only one updated contact", 1, updatedContacts.size());
        Assert.assertEquals("Should find updated contact", contact, updatedContacts.stream().findFirst().get());
    }

    @Test
    public void shouldNotFindUpdatedContact() throws Exception {
        Contact nthird = new Contact();
        contacts.add(nthird);
        nthird.setFirstName("Third");
        nthird.setLastName("TCon");
        nthird.getEmails().addAll(Lists.newArrayList("third3@gmail.com", "third33@gmail.com"));

        List<Contact> newContacts = VCardServiceImpl.findUpdatedContacts(savedContacts, contacts);

        Assert.assertTrue("Should not find update contact", newContacts.isEmpty());
    }
}