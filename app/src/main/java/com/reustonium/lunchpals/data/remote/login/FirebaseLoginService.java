package com.reustonium.lunchpals.data.remote.login;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.reustonium.lunchpals.data.model.LoginResult;
import com.reustonium.lunchpals.data.model.User;
import com.reustonium.lunchpals.data.remote.Util;
import com.reustonium.lunchpals.data.remote.Utils;

import javax.inject.Inject;

public class FirebaseLoginService implements LoginService {

    Firebase mFirebaseRef;

    public FirebaseLoginService(Firebase firebase) {
        this.mFirebaseRef = firebase;
    }

    @Override
    public LoginResult signinWithEmail(final String email, final String password) {

        final LoginResult mLoginResult = new LoginResult();

        mFirebaseRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {

            @Override
            public void onAuthenticated(AuthData authData) {
                final String unprocessedEmail = authData.getProviderData()
                        .get(Util.FIREBASE_PROPERTY_EMAIL).toString().toLowerCase();
                String mEncodedEmail = Utils.encodeEmail(unprocessedEmail);
                mLoginResult.encodedEmail = mEncodedEmail;

                final Firebase userRef = new Firebase(Util.FIREBASE_URL_USERS).child(mEncodedEmail);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            /**
                             * If recently registered user has hasLoggedInWithPassword = "false"
                             * (never logged in using password provider)
                             */
                            if (!user.isHasLoggedInWithPassword()) {

                                /**
                                 * Change password if user that just signed in signed up recently
                                 * to make sure that user will be able to use temporary password
                                 * from the email more than 24 hours
                                 */
                                mFirebaseRef.changePassword(unprocessedEmail,
                                        email, password, new Firebase.ResultHandler() {
                                    @Override
                                    public void onSuccess() {
                                        userRef.child(Util.FIREBASE_PROPERTY_USER_HAS_LOGGED_IN_WITH_PASSWORD).setValue(true);
                                    }

                                    @Override
                                    public void onError(FirebaseError firebaseError) {
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                /**
                 * Use utility method to check the network connection state
                 * Show "No network connection" if there is no connection
                 * Show Firebase specific error message otherwise
                 */
                switch (firebaseError.getCode()) {
                    case FirebaseError.INVALID_EMAIL:
                    case FirebaseError.USER_DOES_NOT_EXIST:
                       mLoginResult.error = Util.error_message_email_issue;
                        break;
                    case FirebaseError.INVALID_PASSWORD:
                        mLoginResult.error = Util.error_message_wrong_password;
                        break;
                    case FirebaseError.NETWORK_ERROR:
                        mLoginResult.error = Util.error_message_failed_sign_in_no_network;
                        break;
                    default:
                        mLoginResult.error = Util.error_message_default;
                }

            }
        });
        return mLoginResult;
    }
}
