package org.arthan.semantic.service.data.impl;

import org.arthan.semantic.service.data.PropertyService;

import java.util.Properties;

/**
 * Created by artur.shamsiev on 04.06.2015
 */
public abstract class AbstractPropertyService implements PropertyService {
    @Override
    public String get(String prop) {
        return findProps().getProperty(prop);
    }

    abstract Properties findProps();
}
