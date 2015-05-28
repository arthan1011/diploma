package org.arthan.semantic.service.graph;

import org.arthan.semantic.model.Res;

import java.util.List;

/**
 * Created by artur.shamsiev on 28.05.2015
 */
public interface GraphSemanticService {
    List<Res> allResourcesForTypes(List<String> classesList);
}
