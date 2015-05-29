package org.arthan.semantic.service.graph;

import org.arthan.semantic.model.Res;
import org.arthan.semantic.model.Triplet;

import java.util.List;

/**
 * Created by artur.shamsiev on 28.05.2015
 */
public interface GraphSemanticService {
    List<Res> allResourcesForTypes(List<String> classesList);

    /**
     * Создает новый ресурс из субъекта триплета и устанавливает связь между ним и существующим объектом,
     * uri которого указан в объекте триплета. Также добавляет к ресурсу свойство LABEL, значение которого
     * определено в <br/> {@code triplet.getSubject().getLabel()}
     * @param triplet новый триплет
     */
    void addNewResourceTriplet(Triplet triplet);
}
