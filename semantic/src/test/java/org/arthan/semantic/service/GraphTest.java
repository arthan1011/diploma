package org.arthan.semantic.service;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.junit.Test;

import java.io.File;

/**
 * Created by Arthur Shamsiev on 27.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public class GraphTest {

    @Test
    public void testJenaGraph() throws Exception {
        Model model = ModelFactory.createDefaultModel();

        GraphVCardService cardService = new GraphVCardService();

        File defaultVCardFile = new File(System.getProperty("user.home") + "/.semantic/list.vcf");
        cardService.addToModel(defaultVCardFile, model);

        model.write(System.out);
    }
}