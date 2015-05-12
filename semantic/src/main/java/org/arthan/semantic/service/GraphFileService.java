package org.arthan.semantic.service;

/**
 * Created by artur.shamsiev on 08.05.2015
 */
public interface GraphFileService {

    void addImageToGraphForContact(String filePath, String contactID);

    void addDocumentToGraphForContact(String documentPath, String documentName, String contactID);
}
