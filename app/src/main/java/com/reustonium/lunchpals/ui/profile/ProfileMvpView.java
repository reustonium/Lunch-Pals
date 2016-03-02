package com.reustonium.lunchpals.ui.profile;

import com.reustonium.lunchpals.ui.base.MvpView;

public interface ProfileMvpView extends MvpView {

    void showProfile(String pal);

    void showError();

}
