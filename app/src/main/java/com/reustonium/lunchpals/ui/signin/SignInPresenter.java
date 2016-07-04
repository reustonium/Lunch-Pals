package com.reustonium.lunchpals.ui.signin;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.reustonium.lunchpals.ui.base.BasePresenter;
import com.reustonium.lunchpals.ui.signup.SignUpPresenter;

import javax.inject.Inject;

public class SignInPresenter extends BasePresenter<SignInView> {

    private static final String TAG = SignUpPresenter.class.getName();
    FirebaseAuth mAuth;

    @Inject
    public SignInPresenter() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            getMvpView().showError(task.getException().getMessage().toString());
                        }

                        else {
                            Log.w(TAG, "FUCK YEAH BRAH!");
                        }
                    }
                });
    }
}
