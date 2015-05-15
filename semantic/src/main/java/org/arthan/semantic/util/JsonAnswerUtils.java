package org.arthan.semantic.util;

import org.json.JSONStringer;

/**
 * Created by artur.shamsiev on 15.05.2015
 */
public class JsonAnswerUtils {
    public static String fileAddedAnswer() {
        return new JSONStringer()
            .object()
                .key("answer")
                .object()
                    .key("status")
                    .value("added")
                .endObject()
            .endObject()
        .toString();
    }

    public static String notInHomeAnswer() {
        return new JSONStringer()
            .object()
                .key("answer")
                .object()
                    .key("status")
                    .value("not-user")
                .endObject()
            .endObject()
        .toString();
    }
}
