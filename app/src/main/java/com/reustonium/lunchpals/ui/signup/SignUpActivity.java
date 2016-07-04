package com.reustonium.lunchpals.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.ui.base.BaseActivity;
import com.reustonium.lunchpals.ui.signin.SignInActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import javax.inject.Inject;

public class SignUpActivity extends BaseActivity implements SignUpView {

    @Inject SignUpPresenter mSignUpPresenter;

    @BindView(R.id.signup_password_layout) TextInputLayout mPasswordLayout;
    @BindView(R.id.signup_password_edit_text) EditText mPasswordView;
    @BindView(R.id.signup_email_layout) TextInputLayout mEmailLayout;
    @BindView(R.id.signup_email_edit_text) EditText mEmailView;
    @BindView(R.id.signup_btn) Button mSignUpButton;
    @BindView(R.id.signup_progress) ProgressBar mProgressBar;
    @BindView(R.id.signup_email_login_form) View mEmailLoginForm;
    @BindView(R.id.signup_username_edit_text) EditText mUsernameView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mSignUpPresenter.attachView(this);

        if (getIntent().hasExtra("EXTRA_EMAIL")) {
            String email = getIntent().getStringExtra("EXTRA_EMAIL");
            mEmailView.setText(email);
        }

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

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
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
                mEmailLayout.setError("Oh Noes! Looks like you forgot an email address.");
                cancel = true;
            }

            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                mEmailLayout.setError("Doh! Are you sure that's your email address?");
                cancel = true;
            } else {
                mEmailLayout.setErrorEnabled(false);
            }

            if (password.length() < 8) {
                mPasswordLayout.setError("Opps! Passwords should be at least 8 characters long.");
                cancel = true;
            } else {
                mPasswordLayout.setErrorEnabled(false);
            }

            if (!cancel) {
                mSignUpPresenter.signUpNewUser(username, email, password);
            } else {
                hideProgress();
            }
}
    @OnClick(R.id.signup_signin_text)
    public void signUp() {
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        intent.putExtra("EXTRA_EMAIL", mEmailView.getText().toString());
        startActivity(intent);
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
