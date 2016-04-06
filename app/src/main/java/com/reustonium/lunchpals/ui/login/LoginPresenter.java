package com.reustonium.lunchpals.ui.login;

import com.reustonium.lunchpals.data.DataManager;
import com.reustonium.lunchpals.data.model.User;
import com.reustonium.lunchpals.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

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


    public void checkAuthState() {
    }

    public void loadDemoPals() {
        checkViewAttached();
        List<User> pals = new ArrayList<User>();
        HashMap<String, Object> map = new HashMap<>();
        pals.add(new User("Andy", "a@b.com", map));
        pals.add(new User("Jimmy", "a@b.com", map));
        pals.add(new User("Larry", "a@b.com", map));
        pals.add(new User("Moe", "a@b.com", map));
        getMvpView().showPals(pals);
    }
}
