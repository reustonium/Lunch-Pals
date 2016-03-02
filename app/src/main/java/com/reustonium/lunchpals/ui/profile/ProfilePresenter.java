package com.reustonium.lunchpals.ui.profile;

import com.reustonium.lunchpals.ui.base.BasePresenter;

import javax.inject.Inject;

public class ProfilePresenter extends BasePresenter<ProfileMvpView> {

    @Inject public ProfilePresenter(){}

    @Override
    public void attachView(ProfileMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
