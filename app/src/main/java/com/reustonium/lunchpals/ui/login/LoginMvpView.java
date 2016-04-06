package com.reustonium.lunchpals.ui.login;

import com.reustonium.lunchpals.data.model.User;
import com.reustonium.lunchpals.ui.base.MvpView;

import java.util.List;

/**
 * Created by Andrew on 3/21/2016.
 */
public interface LoginMvpView extends MvpView {
    void showPals(List<User> pals);
    void showGeneralError(String error);
    void onLoginSuccess(String result);
}
