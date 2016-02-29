package com.reustonium.lunchpals.ui.main;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.reustonium.lunchpals.ui.base.BasePresenter;

/**
 * Created by andrew on 2/25/16.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {

    @Inject public MainPresenter(){}

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadPals() {
        checkViewAttached();
        //Dummy some data
        List<String> mPals = new ArrayList<>();
        mPals.add("Andy");
        mPals.add("Jimmy Jamm");
        mPals.add("Shaq Fu");
        getmMvpView().showPals(mPals);
    }
}
