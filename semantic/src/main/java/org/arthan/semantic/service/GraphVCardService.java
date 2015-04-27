package org.arthan.semantic.service;

import com.hp.hpl.jena.rdf.model.Model;
import ezvcard.VCard;
import org.arthan.semantic.graph.ModelWrapper;

import java.io.File;
import java.util.List;

/**
 * Created by Arthur Shamsiev on 27.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public class GraphVCardService {
    public void addToModel(File vCardFile, ModelWrapper modelWrapper) {
        List<VCard> vCards = VCardUtils.readVCard(vCardFile);
    }

    // http://artur.lazy-magister.org/contacts/17
}
