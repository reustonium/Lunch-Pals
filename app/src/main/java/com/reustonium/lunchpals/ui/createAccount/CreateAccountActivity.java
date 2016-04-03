package com.reustonium.lunchpals.ui.createAccount;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.data.remote.Util;
import com.reustonium.lunchpals.ui.base.BaseActivity;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateAccountActivity extends BaseActivity implements CreateAccountMvpView {

    private static final String LOG_TAG = CreateAccountActivity.class.getSimpleName();
    private ProgressDialog mAuthProgressDialog;
    private String mUserName, mUserEmail, mPassword;

    private SecureRandom mRandom = new SecureRandom();

    @Inject CreateAccountPresenter mCreateAccountPresenter;

    @Bind(R.id.edit_text_username_create) EditText mEditTextUsernameCreate;
    @Bind(R.id.edit_text_email_create) EditText mEditTextEmailCreate;
    @Bind(R.id.linear_layout_create_account_activity)
    LinearLayout linearLayoutCreateAccountActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_create_account);
        mCreateAccountPresenter.attachView(this);
        ButterKnife.bind(this);

        initializeBackground(linearLayoutCreateAccountActivity);

        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getResources().getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getResources()
                .getString(R.string.progress_dialog_check_inbox));
        mAuthProgressDialog.setCancelable(false);
    }

    private void initializeBackground(LinearLayout linearLayout) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linearLayout.setBackgroundResource(R.drawable.background_loginscreen_land);
        } else {
            linearLayout.setBackgroundResource(R.drawable.background_loginscreen);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // MvpView Methods
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreateAccountPressed(View view) {
        mUserName = mEditTextUsernameCreate.getText().toString();
        mUserEmail = mEditTextEmailCreate.getText().toString().toLowerCase();
        mPassword = new BigInteger(130, mRandom).toString(32);

        /**
         * Check that email and user name are okay
         */
        boolean validEmail = isEmailValid(mUserEmail);
        boolean validUserName = isUserNameValid(mUserName);
        if (!validEmail || !validUserName) return;

        /**
         * If everything was valid show the progress dialog to indicate that
         * account creation has started
         */
        mAuthProgressDialog.show();
        mCreateAccountPresenter.createAccount(mUserEmail, mPassword);
    }

    @Override
    public void onPasswordReset() {
        mAuthProgressDialog.dismiss();
        Log.i(LOG_TAG, getString(R.string.log_message_auth_successful));

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(CreateAccountActivity.this);
        SharedPreferences.Editor spe = sp.edit();

        /**
         * Save name and email to sharedPreferences to create User database record
         * when the registered user will sign in for the first time
         */
        spe.putString(Util.KEY_SIGNUP_EMAIL, mUserEmail).apply();

        /**
         *  Password reset email sent, open app chooser to pick app
         *  for handling inbox email intent
         */
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
        try {
            startActivity(intent);
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
                                    /* User does not have any app to handle email */
        }
    }

    private boolean isEmailValid(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEditTextEmailCreate
                    .setError(String.format(getString(R.string.error_invalid_email_not_valid),
                    email));
            return false;
        }
        return isGoodEmail;
    }

    private boolean isUserNameValid(String userName) {
        if (userName.equals("")) {
            mEditTextUsernameCreate
                    .setError(getResources().getString(R.string.error_cannot_be_empty));
            return false;
        }
        return true;
    }

    @Override
    public void showError(String message) {
        mAuthProgressDialog.dismiss();
        Toast.makeText(CreateAccountActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
