package org.arthan.semantic.service.middle;

/**
 * Created by artur.shamsiev on 07.05.2015
 */
public interface FileService {

    String addImage(String absSysImagePath,
                    String absWebImagePath,
                    String id);

    String addDocument(String filePath,
                       String contactID);

    String findDocument(String documentID);

    String addFile(String filePath, String predicateURI, String objectURI);
}
