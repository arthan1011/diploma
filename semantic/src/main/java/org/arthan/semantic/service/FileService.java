package org.arthan.semantic.service;

/**
 * Created by artur.shamsiev on 07.05.2015
 */
public interface FileService {

    String addImage(String absSysImagePath,
                    String absWebImagePath,
                    String id);

    String addDocument(String filePath,
                       String contactID);
}
