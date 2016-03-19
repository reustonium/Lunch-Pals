package com.reustonium.lunchpals.ui.main;

import java.util.List;

import javax.inject.Inject;

import com.reustonium.lunchpals.data.DataManager;
import com.reustonium.lunchpals.ui.base.BasePresenter;

/**
 * Created by andrew on 2/25/16.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;

    @Inject public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

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
        List<String> mPals = mDataManager.getPals();
        if (mPals == null || mPals.isEmpty()) {
            getMvpView().showPalsEmpty();
        }
        getMvpView().showPals(mPals);
    }

}
