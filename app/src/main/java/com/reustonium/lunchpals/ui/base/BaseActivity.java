package com.reustonium.lunchpals.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.reustonium.lunchpals.LunchPalsApplication;
import com.reustonium.lunchpals.injection.component.ActivityComponent;
import com.reustonium.lunchpals.injection.component.DaggerActivityComponent;
import com.reustonium.lunchpals.injection.module.ActivityModule;
import com.reustonium.lunchpals.ui.signup.SignUpActivity;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = SignUpActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out:");
                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(LunchPalsApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }
}
