package com.reustonium.lunchpals;

import com.reustonium.lunchpals.data.PalRepositories;
import com.reustonium.lunchpals.data.PalsRepository;
import com.reustonium.lunchpals.data.PalsServiceApiImpl;
import com.reustonium.lunchpals.util.ImageFile;
import com.reustonium.lunchpals.util.ImageFileImpl;

/**
 * Created by andrew on 2/4/16.
 */
public class Injection {
    public static ImageFile provideImageFile() {
        return new ImageFileImpl();
    }

    public static PalsRepository providePalsRepository() {
        return PalRepositories.getInMemoryRepoInstance(new PalsServiceApiImpl());
    }
}
