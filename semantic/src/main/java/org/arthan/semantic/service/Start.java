package org.arthan.semantic.service;

import org.arthan.semantic.service.graph.GraphService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by artur.shamsiev on 28.04.2015.
 */

@Component
public class Start implements InitializingBean {

    @Autowired
    GraphVCardService graphVCardService;
    @Autowired
    GraphService graphService;

    @Override
    public void afterPropertiesSet() throws Exception {
        refreshGraph();
    }

    private void refreshGraph() {
        graphService.refreshGraph();
    }
}
