package org.arthan.semantic.service.graph;

import org.arthan.semantic.service.VCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by artur.shamsiev on 30.04.2015
 */

@Service
public class GraphService {
    @Autowired
    VCardService cardService;
    @Autowired
    GraphRepository graphRep;
    @Autowired
    GraphMBoxService graphMBoxService;

    public void refreshGraph() {
        cardService.addToGraph();
        graphMBoxService.addToGraph();

        graphRep.writeGraph();
    }
}
