package com.reustonium.lunchpals.ui.main;

import java.util.List;

import com.reustonium.lunchpals.data.model.Ribot;
import com.reustonium.lunchpals.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showRibots(List<Ribot> ribots);

    void showRibotsEmpty();

    void showError();

}
