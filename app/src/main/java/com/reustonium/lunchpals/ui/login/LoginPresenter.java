package com.reustonium.lunchpals.ui.login;

import com.reustonium.lunchpals.data.DataManager;
import com.reustonium.lunchpals.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Andrew on 3/21/2016.
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

    public void signinWithEmail(String email, String password) {
        String result = mDataManager.signinWithEmail(email, password);
        checkViewAttached();
        //On failure do stuff
        switch (result.toLowerCase()) {
            case "success":
                getMvpView().onLoginSuccess(null, email);
                break;
            case "error":
                getMvpView().showLoginError("EVERYTHING SUX");
        }
    }
}
