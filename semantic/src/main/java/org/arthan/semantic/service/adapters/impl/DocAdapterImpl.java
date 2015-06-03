package org.arthan.semantic.service.adapters.impl;

import org.arthan.semantic.model.File;
import org.arthan.semantic.service.adapters.DocAdapter;
import org.springframework.stereotype.Component;

/**
 * Created by artur.shamsiev on 03.06.2015
 */

@Component("docAdapter")
public class DocAdapterImpl implements DocAdapter {
    @Override
    public void addToGraph(File file, String predicateURI, String objectURI) {

    }
}
