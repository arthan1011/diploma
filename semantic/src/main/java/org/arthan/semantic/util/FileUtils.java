package org.arthan.semantic.util;

import java.io.*;
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

    public static final String DEFAULT_MODEL_FILE = System.getProperty("user.home") + prop.getProperty("path.default.model");

    public static InputStream modelIS() {
        String path = DEFAULT_MODEL_FILE;
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public static OutputStream modelOS() {
        File file = new File(DEFAULT_MODEL_FILE);
        FileOutputStream os = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            os = new FileOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    public static boolean modelFileExists() {
        return new File(DEFAULT_MODEL_FILE).exists();
    }
}
