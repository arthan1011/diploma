package org.arthan.semantic.service.middle.impl;

import org.arthan.semantic.model.Contact;
import org.arthan.semantic.model.File;
import org.arthan.semantic.model.MP3File;
import org.arthan.semantic.service.graph.GraphFileService;
import org.arthan.semantic.service.graph.GraphMusicService;
import org.arthan.semantic.service.graph.GraphVCardService;
import org.arthan.semantic.service.middle.UserService;
import org.json.JSONStringer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by artur.shamsiev on 13.05.2015
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private GraphVCardService graphVCardService;
    @Autowired
    private GraphFileService graphFileService;
    @Autowired
    private GraphMusicService graphMusicService;

    @Override
    public String findUserInfo() {
        List<Contact> contactList = graphVCardService.allContacts();
        List<File> documentList = graphFileService.allUserDocuments();
        List<MP3File> musicList = graphMusicService.allUserMusic();
        String userInfoJson = new JSONStringer()
            .object()
                .key("main")
                .object()
                    .key("contacts")
                    .value(contactList)
                    .key("documents")
                    .value(documentList)
                    .key("musics")
                    .value(musicList)
                .endObject()
            .endObject()
        .toString();
        return userInfoJson;
    }
}
