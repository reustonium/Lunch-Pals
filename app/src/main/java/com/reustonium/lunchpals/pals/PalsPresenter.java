package com.reustonium.lunchpals.pals;

import android.support.annotation.NonNull;

import com.reustonium.lunchpals.data.Pal;
import com.reustonium.lunchpals.data.PalsRepository;
import com.reustonium.lunchpals.util.EspressoIdlingResource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by andrew on 2/4/16.
 */
public class PalsPresenter implements PalsContract.UserActionsListener{

    private final PalsRepository mPalsRepository;
    private final PalsContract.View mPalsView;

    public PalsPresenter(@NonNull PalsRepository palsRepository, @NonNull PalsContract.View palsView){
        mPalsRepository = checkNotNull(palsRepository, "palsRepository can't be null!");
        mPalsView = checkNotNull(palsView, "palsView can't be null!");
    }


    @Override
    public void loadPals(boolean forceUpdate) {
        mPalsView.setProgressIndicator(true);
        if (forceUpdate) {
            mPalsRepository.refreshData();
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        mPalsRepository.getPals(new PalsRepository.LoadPalsCallback() {
            @Override
            public void onPalsLoaded(List<Pal> pals) {
                EspressoIdlingResource.decrement(); // Set app as idle.
                mPalsView.setProgressIndicator(false);
                mPalsView.showPals(pals);
            }
        });
    }

    @Override
    public void addNewPal() {
        mPalsView.showAddPal();
    }

    @Override
    public void openPalDetails(@NonNull Pal requestedPal) {
        checkNotNull(requestedPal, "requestedPal cannot be null!");
        mPalsView.showPalDetailUi(requestedPal.getId());
    }
}
