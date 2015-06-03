package org.arthan.semantic.service.adapters;

/**
 * Created by artur.shamsiev on 03.06.2015
 */
public interface ResourceAdapter {
    void addToGraph(String filePath, String predicateURI, String objectURI);
}
