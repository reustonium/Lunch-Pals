package com.reustonium.lunchpals.pals;

import android.support.annotation.NonNull;

import com.reustonium.lunchpals.data.Pal;

import java.util.List;

/**
 * Created by andrew on 2/4/16.
 */
public interface PalsContract {
    interface View{
        void setProgressIndicator(boolean active);

        void showPals(List<Pal> pals);

        void showAddPal();

        void showPalDetailUi(String palId);
    }
    interface UserActionsListener{

        void loadPals(boolean forceUpdate);

        void addNewPal();

        void openPalDetails(@NonNull Pal requestedPal);
    }
}
