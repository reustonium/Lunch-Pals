package com.reustonium.lunchpals;

import com.reustonium.lunchpals.data.FakePalsServiceApiImpl;
import com.reustonium.lunchpals.data.PalRepositories;
import com.reustonium.lunchpals.data.PalsRepository;
import com.reustonium.lunchpals.util.FakeImageFileImpl;
import com.reustonium.lunchpals.util.ImageFile;

/**
 * Created by andrew on 2/4/16.
 */
public class Injection {

    public static ImageFile provideImageFile() {
        return new FakeImageFileImpl();
    }

    public static PalsRepository providePalsRepository() {
        return PalRepositories.getInMemoryRepoInstance(new FakePalsServiceApiImpl());
    }
}