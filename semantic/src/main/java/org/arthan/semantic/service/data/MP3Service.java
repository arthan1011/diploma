package org.arthan.semantic.service.data;

import org.arthan.semantic.model.MP3File;

/**
 * Отвечает за чтение mp3 файла в файловой системе и преобразование его в
 * объект {@link org.arthan.semantic.model.MP3File}<br/>
 *
 * <p>Created by artur.shamsiev on 14.05.2015<p/>
 */
public interface MP3Service {

    MP3File findMP3(String absMp3Path);
}
