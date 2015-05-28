package org.arthan.semantic.service.graph.impl;

import org.arthan.semantic.model.Res;
import org.arthan.semantic.service.graph.GraphRepository;
import org.arthan.semantic.service.graph.GraphSemanticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by artur.shamsiev on 28.05.2015
 */

@Component
public class GraphSemanticServiceImpl implements GraphSemanticService {

    @Autowired
    GraphRepository graphRep;

    @Override
    public List<Res> allResourcesForTypes(List<String> classesList) {
        List<Res> resultList = classesList.stream()
                .flatMap(typeUri -> graphRep.findResourcesWithType(typeUri).stream())
                .map(Res::new)
                .collect(Collectors.toList());

        return resultList;
    }
}
