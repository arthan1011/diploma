package org.arthan.semantic.model;

import org.arthan.semantic.util.FileUtils;

/**
 * Created by artur.shamsiev on 08.05.2015
 */
public class File {
    public static final String URI = "file://artur.lazy-magister.org/data/";

    protected String path;
    protected String title;
    protected Contact creator;

    private File(String path) {
        this.path = path;
        this.title = FileUtils.extractFileName(path);
    }

    public File() {
    }

    public static File fromURI(String uri) {
        if (!uri.startsWith(URI)) {
            throw new IllegalArgumentException("File uri should starts with " + URI);
        }
        return new File(uri.substring(URI.length()));
    }

    public static File fromPath(String path) {
        return new File(path);
    }

    public static File fromAbsPath(String absPath) {
        String path = FileUtils.toUnixPath(FileUtils.cutOffUserHome(absPath));
        return new File(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Contact getCreator() {
        return creator;
    }

    public void setCreator(Contact creator) {
        this.creator = creator;
    }

    public String getUriID() {
        return URI + path;
    }

    @Override
    public String toString() {
        return path;
    }
}
