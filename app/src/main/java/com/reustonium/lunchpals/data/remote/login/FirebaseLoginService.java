package com.reustonium.lunchpals.data.remote.login;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.Subscriptions;

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

    @Override
    public Observable<AuthData> checkAuthState() {
        return Observable.create(new Observable.OnSubscribe<AuthData>() {
            @Override
            public void call(final Subscriber<? super AuthData> subscriber) {
                final Firebase.AuthStateListener listener = mFirebase
                        .addAuthStateListener(new Firebase.AuthStateListener() {
                            @Override
                            public void onAuthStateChanged(AuthData authData) {
                                subscriber.onNext(authData);
                            }
                        });

                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        mFirebase.removeAuthStateListener(listener);
                    }
                }));
            }
        }).startWith(mFirebase.getAuth()).distinctUntilChanged();

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
