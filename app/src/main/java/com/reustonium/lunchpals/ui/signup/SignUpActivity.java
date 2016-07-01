package com.reustonium.lunchpals.ui.signup;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import javax.inject.Inject;

public class SignUpActivity extends BaseActivity implements SignUpView {

    @Inject SignUpPresenter mSignUpPresenter;

    @BindView(R.id.password) EditText mPasswordView;
    @BindView(R.id.email) EditText mEmailView;
    @BindView(R.id.sign_in_btn) Button mSignInButton;
    @BindView(R.id.login_progress) ProgressBar mProgressBar;
    @BindView(R.id.email_login_form) View mEmailLoginForm;
    @BindView(R.id.username) EditText mUsernameView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mSignUpPresenter.attachView(this);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 769 || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    showProgress();
                    createUser();
                    // don't consume the event, so the keyboard can also be hidden
                    // http://stackoverflow.com/questions/2342620/how-to-hide-keyboard-after-typing-in-edittext-in-android#comment20849208_10184099
                    return false;
                }
                return false;
            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                createUser();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSignUpPresenter.detachView();
    }

    private void createUser() {

        String username = mUsernameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("Oh Noes! Looks like you forgot an email address.");
            cancel = true;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailView.setError("Doh! Are you sure that's your email address?");
            cancel = true;
        }

        if (password.length() < 8) {
            mPasswordView.setError("Opps! Passwords should be at least 8 characters long.");
            cancel = true;
        }

        if (!cancel) {
            mSignUpPresenter.signUpNewUser(username, email, password);
        } else {
            hideProgress();
        }
}

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mEmailLoginForm.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mEmailLoginForm.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        hideProgress();
        Snackbar.make(mEmailLoginForm, message, Snackbar.LENGTH_LONG).show();
    }
}
