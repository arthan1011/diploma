package org.arthan.semantic.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

/**
 * Created by Arthur Shamsiev on 29.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public class FileUtils {
    public static final String USER_HOME = System.getProperty("user.home");
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

    /**
     * Копирует файл из одного места в другое. Если файл-назначение существует, перезаписывае его.
     *
     * @param absSource абсолютный путь до исходного файла
     * @param absTarget абсолютный путь до назначения
     */
    public static void copyFile(String absSource, String absTarget) {
        // such file not exists in web app data directory
        File targetFile = new File(absTarget);

        if (targetFile.exists()) {
            targetFile.delete();
        }

        try {
            Files.copy(
                    new FileInputStream(absSource),
                    targetFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean inHomeDirectory(String filePath) {
        return filePath.startsWith(USER_HOME);
    }

    /**
     * Отсекает от имени файла путь до домашнего каталога пользователя
     *
     * @param imagePath путь до файла в домашнем каталоге
     * @return
     */
    public static String cutOffUserHome(String imagePath) {
        if (!inHomeDirectory(imagePath)) {
            return imagePath;
        }
        return imagePath.substring(USER_HOME.length() + 1);
    }

    public static String toUnixPath(String path) {
        return path.replaceAll("\\\\", "/");
    }

    public static String extractFileName(String absFilePath) {
        Preconditions.checkNotNull(Strings.emptyToNull(absFilePath));
        String[] pathArray = absFilePath.split("/");
        return pathArray[pathArray.length - 1].split("\\.")[0];
    }
}
