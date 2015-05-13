package org.arthan.semantic.service.impl;

import org.arthan.semantic.service.GraphFileService;
import org.arthan.semantic.service.FileService;
import org.arthan.semantic.util.FileUtils;
import org.json.JSONStringer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by artur.shamsiev on 07.05.2015
 */

@Component
public class FileServiceImpl implements FileService {


    @Autowired
    private GraphFileService graphFileService;

    private void addImageToGraphForContact(String absSysImagePath, String contactID) {
        String image = FileUtils.cutOffUserHome(absSysImagePath);
        image = FileUtils.toUnixPath(image);
        graphFileService.addImageToGraphForContact(image, contactID);
        // file:///data/Pictures/Desert.jpg
    }

    @Override
    public String addImage(String absSysImagePath,
                           String absWebImagePath,
                           String id) {

        if (!FileUtils.inHomeDirectory(absSysImagePath)) {
            return notInHomeAnswer();
        }

        copyFileToServer(absSysImagePath, absWebImagePath);
        addImageToGraphForContact(absSysImagePath, id);

        return fileAddedAnswer();
    }

    @Override
    public String addDocument(String absSysDocPath, String contactID) {
        if (!FileUtils.inHomeDirectory(absSysDocPath)) {
            return notInHomeAnswer();
        }

        addDocumentToGraphForContact(absSysDocPath, contactID);

        return fileAddedAnswer();
    }

    private void addDocumentToGraphForContact(String absSysDocPath, String contactID) {
        String documentPath = FileUtils.cutOffUserHome(absSysDocPath);
        documentPath = FileUtils.toUnixPath(documentPath);
        String documentName = FileUtils.extractFileName(documentPath);

        graphFileService.addDocumentToGraphForContact(
                documentPath,
                documentName,
                contactID
        );
    }

    private String fileAddedAnswer() {
        return new JSONStringer()
            .object()
                .key("answer")
                .object()
                    .key("status")
                    .value("added")
                .endObject()
            .endObject()
        .toString();
    }

    private String notInHomeAnswer() {
        return new JSONStringer()
            .object()
                .key("answer")
                .object()
                    .key("status")
                    .value("not-user")
                .endObject()
            .endObject()
        .toString();
    }

    private void copyFileToServer(String absSysImagePath, String absWebImagePath) {
        FileUtils.copyFile(absSysImagePath, absWebImagePath);
    }

}
