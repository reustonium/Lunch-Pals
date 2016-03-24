package com.reustonium.lunchpals.ui.login;

import com.reustonium.lunchpals.data.DataManager;
import com.reustonium.lunchpals.data.model.LoginResult;
import com.reustonium.lunchpals.data.remote.Util;
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
        LoginResult result = mDataManager.signinWithEmail(email, password);
        checkViewAttached();

        if (result.error == null) {
            getMvpView().onLoginSuccess(result);
        } else {
            switch (result.error) {
                case Util.error_message_email_issue:
                    getMvpView().showEmailError();
                    break;
                case Util.error_message_wrong_password:
                    getMvpView().showPasswordError();
                    break;
                case Util.error_message_failed_sign_in_no_network:
                    getMvpView().showGeneralError(result.error);
                    break;
                case Util.error_message_default:
                    getMvpView().showGeneralError(result.error);
                    break;
                default:
                    getMvpView().showGeneralError(result.error);

            }
        }
    }
}
