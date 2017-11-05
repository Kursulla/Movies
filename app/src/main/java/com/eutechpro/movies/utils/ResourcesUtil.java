package com.eutechpro.movies.utils;

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

        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader    buffReader  = new BufferedReader(inputReader);
        String            line;
        StringBuilder     text        = new StringBuilder();

        try {
            while ((line = buffReader.readLine()) != null) {
                text.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }
}
