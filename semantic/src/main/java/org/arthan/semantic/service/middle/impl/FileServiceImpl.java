package org.arthan.semantic.service.middle.impl;

import org.arthan.semantic.model.File;
import org.arthan.semantic.service.graph.GraphFileService;
import org.arthan.semantic.service.middle.FileService;
import org.arthan.semantic.util.FileUtils;
import org.arthan.semantic.util.JsonAnswerUtils;
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
        String imageName = FileUtils.extractFileName(image);
        graphFileService.addImageToGraphForContact(image, contactID, imageName);
        // file:///data/Pictures/Desert.jpg
    }

    @Override
    public String addImage(String absSysImagePath,
                           String absWebImagePath,
                           String id) {

        if (!FileUtils.inHomeDirectory(absSysImagePath)) {
            return JsonAnswerUtils.notInHomeAnswer();
        }

        copyFileToServer(absSysImagePath, absWebImagePath);
        addImageToGraphForContact(absSysImagePath, id);

        return JsonAnswerUtils.fileAddedAnswer();
    }

    @Override
    public String addDocument(String absSysDocPath, String contactID) {
        if (!FileUtils.inHomeDirectory(absSysDocPath)) {
            return JsonAnswerUtils.notInHomeAnswer();
        }

        addDocumentToGraphForContact(absSysDocPath, contactID);

        return JsonAnswerUtils.fileAddedAnswer();
    }

    @Override
    public String findDocument(String documentID) {
        File document = graphFileService.findFileByID(documentID);
        String documentJSON = new JSONStringer()
            .object()
                .key("document")
                .object()
                    .key("title")
                    .value(document.getTitle())
                    .key("creator")
                    .object()
                        .key("id")
                        .value(document.getCreator().getId())
                        .key("firstName")
                        .value(document.getCreator().getFirstName())
                        .key("lastName")
                        .value(document.getCreator().getLastName())
                    .endObject()
                .endObject()
            .endObject()
        .toString();
        return documentJSON;
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

    private void copyFileToServer(String absSysImagePath, String absWebImagePath) {
        FileUtils.copyFile(absSysImagePath, absWebImagePath);
    }

}
