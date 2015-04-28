package org.arthan.semantic.service;

import org.arthan.semantic.graph.ModelWrapper;
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

    @Override
    public void afterPropertiesSet() throws Exception {
        refreshGraph();
    }

    private void refreshGraph() {
        ModelWrapper modelWrapper = ModelWrapper.initModel();
        graphVCardService.addToModel(VCardUtils.getDefaultVCardFile(), modelWrapper);
        modelWrapper.write();
    }
}
