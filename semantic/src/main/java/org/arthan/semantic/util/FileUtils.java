package org.arthan.semantic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Arthur Shamsiev on 29.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public class FileUtils {
    private static Properties prop;

    static {
        try {
            prop = new Properties();
            prop.load(FileUtils.class.getResourceAsStream("/filepath.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String DEFAULT_MODEL_FILE = prop.getProperty("path.default.model");

    public static InputStream modelIS() {
        String path = System.getProperty("user.home") + DEFAULT_MODEL_FILE;
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }
}
