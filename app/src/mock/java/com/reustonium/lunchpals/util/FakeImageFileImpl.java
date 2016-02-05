package com.reustonium.lunchpals.util;

import java.io.IOException;

/**
 * Created by andrew on 2/4/16.
 */
public class FakeImageFileImpl extends ImageFileImpl {

    @Override
    public void create(String name, String extension) throws IOException {
        // Do nothing
    }

    @Override
    public String getPath() {
        return "file:///android_asset/atsl-logo.png";
    }

    @Override
    public boolean exists() {
        return true;
    }
}
