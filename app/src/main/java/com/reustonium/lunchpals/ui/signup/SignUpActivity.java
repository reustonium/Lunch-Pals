package com.reustonium.lunchpals.ui.signup;

import android.os.Bundle;
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

public class SignUpActivity extends BaseActivity implements SignUpView{

    @Inject SignUpPresenter mSignUpPresenter;

    @BindView(R.id.password) EditText mPasswordView;
    @BindView(R.id.email) EditText mEmailView;
    @BindView(R.id.sign_in_btn) Button mSignInButton;
    @BindView(R.id.login_progress) ProgressBar mProgressBar;
    @BindView(R.id.email_login_form) View mEmailLoginForm;

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

        //TODO Validate Inputs

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        mSignUpPresenter.signUpNewUser(email, password);
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
