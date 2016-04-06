package com.reustonium.lunchpals.ui.createAccount;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.ServerValue;
import com.reustonium.lunchpals.data.DataManager;
import com.reustonium.lunchpals.data.model.User;
import com.reustonium.lunchpals.data.remote.Util;
import com.reustonium.lunchpals.data.remote.Utils;
import com.reustonium.lunchpals.ui.base.BasePresenter;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CreateAccountPresenter extends BasePresenter<CreateAccountMvpView> {

    private final DataManager mDataManager;
    private Subscription mCreateAccountSubscription, mSaveAccountSubscription;

    @Inject
    public CreateAccountPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(CreateAccountMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void createAccount(final String email, String password, final String userName) {
        checkViewAttached();

        mCreateAccountSubscription = mDataManager.createUser(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Map<String, Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Map<String, Object> result) {
                        //TODO Reset Password
                        //TODO Auth with Password
                        createUserInFirebaseHelper((String) result.get("uid"), email, userName);
                        getMvpView().onPasswordReset();
                    }
                });
    }

    private void createUserInFirebaseHelper(final String authUserId, String email, String userName) {
        final String encodedEmail = Utils.encodeEmail(email);

        /**
         * Create the user and uid mapping
         */
        HashMap<String, Object> userAndUidMapping = new HashMap<String, Object>();

        /* Set raw version of date to the ServerValue.TIMESTAMP value and save into dateCreatedMap */
        HashMap<String, Object> timestampJoined = new HashMap<>();
        timestampJoined.put(Util.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

        /* Create a HashMap version of the user to add */
        User newUser = new User(userName, encodedEmail, timestampJoined);
        HashMap<String, Object> newUserMap = (HashMap<String, Object>)
                new ObjectMapper().convertValue(newUser, Map.class);

        /* Add the user and UID to the update map */
        userAndUidMapping.put("/" + Util.FIREBASE_LOCATION_USERS + "/" + encodedEmail,
                newUserMap);
        userAndUidMapping.put("/" + Util.FIREBASE_LOCATION_UID_MAPPINGS + "/"
                + authUserId, encodedEmail);

        mSaveAccountSubscription = mDataManager.saveUser(userAndUidMapping)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            mDataManager.signOut();
                        }
                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });

    }
}
