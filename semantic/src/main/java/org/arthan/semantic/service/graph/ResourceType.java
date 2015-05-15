package org.arthan.semantic.service.graph;

/**
 * Created by Arthur Shamsiev on 27.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public enum ResourceType {
    CONTACT("http://artur.lazy-magister.org/types/contact"),
    FILE("file://artur.lazy-magister.org/types/file"),
    USER("file://artur.lazy-magister.org/types/user"),
    MP3FILE("file://artur.lazy-magister.org/types/mp3file");

    private String uri;

    ResourceType(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
