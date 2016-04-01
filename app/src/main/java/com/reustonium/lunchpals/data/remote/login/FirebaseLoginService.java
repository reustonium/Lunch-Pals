package com.reustonium.lunchpals.data.remote.login;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;

import rx.Observable;
import rx.Observer;
import rx.subjects.BehaviorSubject;

public class FirebaseLoginService implements LoginService {

    Firebase mFirebase;

    public FirebaseLoginService(Firebase firebase) {
        mFirebase = firebase;
    }

    @Override
    public Observable<AuthData> authWithPassword(String email, String password) {
        final BehaviorSubject<AuthData> subject = BehaviorSubject.create();
        mFirebase.authWithPassword(email, password, new ObservableAuthResultHandler(subject));
        return subject;
    }

    private class ObservableAuthResultHandler implements Firebase.AuthResultHandler {
        private final Observer<AuthData> mObserver;

        private ObservableAuthResultHandler(Observer<AuthData> observer) {
            this.mObserver = observer;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            mObserver.onNext(authData);
            mObserver.onCompleted();
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            mObserver.onError(new FirebaseException(firebaseError.getMessage()));
        }
    }
}
