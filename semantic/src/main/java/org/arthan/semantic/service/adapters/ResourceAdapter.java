package org.arthan.semantic.service.adapters;

import org.arthan.semantic.model.File;

/**
 * Created by artur.shamsiev on 03.06.2015
 */
public interface ResourceAdapter {
    void addToGraph(File file, String predicateURI, String objectURI);
}
