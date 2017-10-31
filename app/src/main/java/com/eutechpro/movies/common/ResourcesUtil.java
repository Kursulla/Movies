package com.eutechpro.movies.common;

import android.content.res.Resources;
import android.support.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Small util class for various methods for manipulating resources.
 */
public class ResourcesUtil {
    public static String readFromRawFile(@RawRes int resId, Resources resources) {
        InputStream inputStream = resources.openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader    buffreader  = new BufferedReader(inputreader);
        String            line;
        StringBuilder     text        = new StringBuilder();

        try {
            while ((line = buffreader.readLine()) != null) {
                text.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }
}
