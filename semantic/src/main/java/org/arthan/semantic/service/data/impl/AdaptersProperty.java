package org.arthan.semantic.service.data.impl;

import org.arthan.semantic.service.data.PropertyService;
import org.arthan.semantic.util.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * Created by artur.shamsiev on 04.06.2015
 */

@Component
@Qualifier("adapters")
public class AdaptersProperty extends AbstractPropertyService implements PropertyService {
    Properties properties;

    @PostConstruct
    public void init() {
        properties = FileUtils.getProperties("adapters.properties");
    }

    @Override
    Properties findProps() {
        return properties;
    }
}
