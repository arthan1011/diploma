package org.arthan.semantic.util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/**
 * Created by Arthur Shamsiev on 29.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public class FileUtils {
    public static final String USER_HOME = System.getProperty("user.home");
    public static Properties prop;

    static {
        try {
            prop = new Properties();
            prop.load(FileUtils.class.getResourceAsStream("/filepath.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String DEFAULT_MODEL_FILE = System.getProperty("user.home") + prop.getProperty("path.default.model");
    public static final String DEFAULT_ONT_MODEL_FILE = System.getProperty("user.home") + prop.getProperty("path.default.ont.model");

    public static InputStream ontModelIS() {
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(DEFAULT_ONT_MODEL_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

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

    public static OutputStreamWriter modelOS() {
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
        return new OutputStreamWriter(os, Charsets.UTF_8);
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

        targetFile.getParentFile().mkdirs();

        try {
            Files.copy(
                    new FileInputStream(absSource),
                    targetFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
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
     * @param absolutePath путь до файла в домашнем каталоге
     * @return
     */
    public static String cutOffUserHome(String absolutePath) {
        if (!inHomeDirectory(absolutePath)) {
            return absolutePath;
        }
        return absolutePath.substring(USER_HOME.length() + 1);
    }

    public static String toUnixPath(String path) {
        return path.replaceAll("\\\\", "/");
    }

    public static String extractFileName(String absFilePath) {
        Preconditions.checkNotNull(Strings.emptyToNull(absFilePath));
        String[] pathArray = absFilePath.split("/");
        return pathArray[pathArray.length - 1].split("\\.")[0];
    }

    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
