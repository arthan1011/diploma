package org.arthan.semantic.service.middle.impl;

import org.arthan.semantic.model.Res;
import org.arthan.semantic.service.graph.GraphSemanticService;
import org.arthan.semantic.service.middle.SemGraphService;
import org.json.JSONStringer;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by artur.shamsiev on 28.05.2015
 */

@Service("semGraphService")
public class SemGraphServiceImpl implements SemGraphService {

    @Inject
    GraphSemanticService graphSemanticService;

    @Override
    public String findInstancesForClasses(List<String> classesList) {
        List<Res> resList = graphSemanticService.allResourcesForTypes(classesList);

        String resourcesJSON = new JSONStringer()
                .object()
                .key("resources")
                .value(resList)
                .endObject()
                .toString();
        return resourcesJSON;
    }
}
