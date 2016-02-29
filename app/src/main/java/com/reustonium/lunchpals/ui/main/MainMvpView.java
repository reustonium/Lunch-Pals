package com.reustonium.lunchpals.ui.main;

import java.util.List;

import com.reustonium.lunchpals.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showPals(List<String> pals);

    void showPalsEmpty();

    void showError();
}
