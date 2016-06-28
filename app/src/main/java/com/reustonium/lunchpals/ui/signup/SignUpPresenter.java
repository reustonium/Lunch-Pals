package com.reustonium.lunchpals.ui.signup;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.reustonium.lunchpals.ui.base.BasePresenter;

import javax.inject.Inject;

public class SignUpPresenter extends BasePresenter<SignUpView> {

    private static final String TAG = SignUpPresenter.class.getName();
    FirebaseAuth mAuth;

    @Inject
    public SignUpPresenter() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void attachView(SignUpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void signUpNewUser(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    getMvpView().showError(task.getException().getMessage());
                }

                else {
                    signInUser(email, password);
                    getMvpView().hideProgress();
                }
            }
        });
    }

    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            getMvpView().showError(task.getResult().toString());
                        }

                        else {
                            Log.w(TAG, "FUCK YEAH BRAH!");
                        }
                    }
                });
    }
}
