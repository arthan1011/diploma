package org.arthan.semantic.model;

/**
 * Created by artur.shamsiev on 08.05.2015
 */
public class File {
    public static final String URI = "file://artur.lazy-magister.org/data/";

    private File(String path) {
        this.path = path;
    }

    public static File fromURI(String uri) {
        if (!uri.startsWith(URI)) {
            throw new IllegalArgumentException("File uri should starts with " + URI);
        }
        return new File(uri.substring(URI.length()));
    }

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}
