package com.reustonium.lunchpals.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.data.model.LoginResult;
import com.reustonium.lunchpals.data.remote.Util;
import com.reustonium.lunchpals.ui.base.BaseActivity;
import com.reustonium.lunchpals.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Inject LoginPresenter mLoginPresenter;

    @Bind(R.id.edit_text_password) EditText mEditTextPasswordInput;
    @Bind(R.id.edit_text_email) EditText mEditTextEmailInput;

    private ProgressDialog mAuthProgressDialog;
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mSharedPrefEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_login);
        mLoginPresenter.attachView(this);
        ButterKnife.bind(this);

        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getString(
                R.string.progress_dialog_authenticating_with_firebase));
        mAuthProgressDialog.setCancelable(false);

        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPrefEditor = mSharedPref.edit();

        /**
         * Call signInPassword() when user taps "Done" keyboard action
         */
        mEditTextPasswordInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    signInPassword();
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    ///////////////////////////////////////////////////////////////////////////
    // MvpView Methods
    ///////////////////////////////////////////////////////////////////////////

    public void onSignInPressed(View view) {
        signInPassword();
    }

    public void onSignUpPressed(View view) {
    }

    @Override
    public void showEmailError() {
        mAuthProgressDialog.dismiss();
        mEditTextEmailInput.setError(getString(R.string.error_message_email_issue));
    }

    @Override
    public void showPasswordError() {
        mEditTextPasswordInput.setError(Util.error_message_wrong_password);
    }

    @Override
    public void showGeneralError(String error) {
        showErrorToast(error);
    }

    @Override
    public void onLoginSuccess(LoginResult result) {
        mAuthProgressDialog.dismiss();
        Log.i(TAG, "email" + " " + getString(R.string.log_message_auth_successful));
        if (result != null) {

                /* Save provider name and encodedEmail for later use and start MainActivity */
            mSharedPrefEditor.putString(Util.KEY_ENCODED_EMAIL, result.encodedEmail).apply();

                /* Go to main activity */
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void signInPassword() {
        String email = mEditTextEmailInput.getText().toString();
        String password = mEditTextPasswordInput.getText().toString();

        /**
         * If email and password are not empty show progress dialog and try to authenticate
         */
        if (email.equals("")) {
            mEditTextEmailInput.setError(getString(R.string.error_cannot_be_empty));
            return;
        }

        if (password.equals("")) {
            mEditTextPasswordInput.setError(getString(R.string.error_cannot_be_empty));
            return;
        }
        mAuthProgressDialog.show();
        mLoginPresenter.signinWithEmail(email, password);
    }

    /**
     * Show error toast to users
     */
    private void showErrorToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
