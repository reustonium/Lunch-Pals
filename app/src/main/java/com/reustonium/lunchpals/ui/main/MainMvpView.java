package com.reustonium.lunchpals.ui.main;

import java.util.List;

import com.reustonium.lunchpals.data.model.User;
import com.reustonium.lunchpals.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showPals(List<User> pals);

    void showPalsEmpty();

    void showError();

}
