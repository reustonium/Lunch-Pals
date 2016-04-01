package com.reustonium.lunchpals.ui.login;

import com.firebase.client.AuthData;
import com.reustonium.lunchpals.data.DataManager;
import com.reustonium.lunchpals.data.remote.Util;
import com.reustonium.lunchpals.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Andrew on 3/21/2016.
 */
public class LoginPresenter extends BasePresenter<LoginMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

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
        checkViewAttached();
        mSubscription = mDataManager.authWithPassword(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AuthData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        switch (e.getMessage()) {
                            case Util.error_message_email_issue:
                                getMvpView().showEmailError();
                            case Util.error_message_wrong_password:
                                getMvpView().showPasswordError();
                            default:
                                getMvpView().showGeneralError(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(AuthData authData) {
                        getMvpView().onLoginSuccess(authData.toString());
                    }
                });
    }

    public void checkAuthState() {
        mDataManager.checkAuthState();
        mSubscription = mDataManager.checkAuthState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<AuthData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AuthData authData) {
                        if (authData != null) {
                            getMvpView().launchMainActivity();
                        }
                    }
                });
    }
}
