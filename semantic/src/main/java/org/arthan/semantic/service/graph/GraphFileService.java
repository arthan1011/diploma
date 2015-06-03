package org.arthan.semantic.service.graph;

import com.hp.hpl.jena.rdf.model.Resource;
import org.arthan.semantic.model.File;

import java.util.List;

/**
 * Created by artur.shamsiev on 08.05.2015
 */
public interface GraphFileService {

    void addImageToGraphForContact(String filePath, String contactID, String imageName);

    Resource addFileToGraph(String fileHomePath);

    void addDocumentToGraphForContact(String documentPath, String documentName, String contactID);

    List<File> findFilesByCreator(String creatorUri);

    List<File> allUserDocuments();

    File findFileByID(String documentID);
}
