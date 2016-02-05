package com.reustonium.lunchpals.util;

import java.io.IOException;

/**
 * Created by andrew on 2/4/16.
 */
public interface ImageFile {
    void create(String name, String extension) throws IOException;

    boolean exists();

    void delete();

    String getPath();
}
