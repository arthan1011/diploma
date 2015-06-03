package org.arthan.semantic.service.adapters.impl;

import org.arthan.semantic.model.File;
import org.arthan.semantic.service.adapters.PdfAdapter;
import org.springframework.stereotype.Component;

/**
 * Created by artur.shamsiev on 03.06.2015
 */

@Component("pdfAdapter")
public class PdfAdapterImpl implements PdfAdapter {
    @Override
    public void addToGraph(File file, String predicateURI, String objectURI) {

    }
}
