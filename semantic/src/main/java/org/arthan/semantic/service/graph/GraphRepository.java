package org.arthan.semantic.service.graph;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.arthan.semantic.util.FileUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Arthur Shamsiev on 29.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GraphRepository {
    private Model model;

    public Model getModel() {
        if (model == null) {
            initModel();
        }
        return model;
    }

    private void initModel() {
        model = ModelFactory.createDefaultModel();
        if (FileUtils.modelFileExists()) {
            model.read(FileUtils.modelIS(), null);
        }
    }

    public void writeGraph() {
        OutputStream out = FileUtils.modelOS();
        model.write(out);
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
