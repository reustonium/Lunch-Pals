package com.reustonium.lunchpals.ui.login;

import com.reustonium.lunchpals.data.DataManager;
import com.reustonium.lunchpals.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by andrew on 3/19/16.
 */
public class LoginPresenter extends BasePresenter<LoginMvpView> {
    private final DataManager mDataManager;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    //Login Specific Logic

}
