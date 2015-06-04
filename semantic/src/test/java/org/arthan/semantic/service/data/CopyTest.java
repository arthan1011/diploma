package org.arthan.semantic.service.data;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

/**
 * Created by artur.shamsiev on 04.06.2015
 */
public class CopyTest {

    @Test
    public void testCopy() throws Exception {
        String source = "C:/Users/artur.shamsiev/Pictures/Penguins.jpg";
        String target = "C:\\Users/artur.shamsiev/Documents/diploma/diploma/semantic/out/artifacts/semantic/data/Pictures/Penguins.jpg";
        File targetFile = new File(target);

        Files.copy(
                new FileInputStream(source),
                targetFile.toPath());

    }
}
