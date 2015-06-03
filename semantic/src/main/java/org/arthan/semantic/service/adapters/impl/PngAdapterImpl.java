package org.arthan.semantic.service.adapters.impl;

import org.arthan.semantic.model.File;
import org.arthan.semantic.service.adapters.PngAdapter;
import org.springframework.stereotype.Component;

/**
 * Created by artur.shamsiev on 03.06.2015
 */

@Component("pngAdapter")
public class PngAdapterImpl implements PngAdapter {
    @Override
    public void addToGraph(File file, String predicateURI, String objectURI) {

    }
}
