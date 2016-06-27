package com.reustonium.lunchpals.ui.signup;

import android.os.Bundle;
import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.ui.base.BaseActivity;

import javax.inject.Inject;

public class SignUpActivity extends BaseActivity {

    @Inject SignUpPresenter mSignUpPresenter;

    

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_sign_up);
    }
}
