package com.reustonium.lunchpals;

import com.firebase.client.AuthData;
import com.firebase.client.FirebaseException;
import com.reustonium.lunchpals.data.DataManager;
import com.reustonium.lunchpals.data.remote.Util;
import com.reustonium.lunchpals.ui.login.LoginMvpView;
import com.reustonium.lunchpals.ui.login.LoginPresenter;
import com.reustonium.lunchpals.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock LoginMvpView mMockLoginMvpView;
    @Mock DataManager mMockDataManager;
    @Mock AuthData mAuthData;
    private LoginPresenter mLoginPresenter;
    String result;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setup() {
        mLoginPresenter = new LoginPresenter(mMockDataManager);
        mLoginPresenter.attachView(mMockLoginMvpView);
    }

    @After
    public void tearDown() {
        mLoginPresenter.detachView();
    }

    @Test
    public void signInWithEmailSuccess() {
        doReturn(Observable.just(mAuthData))
                .when(mMockDataManager)
                .authWithPassword("a@b.com", "password");

        mLoginPresenter.signinWithEmail("a@b.com", "password");
        verify(mMockLoginMvpView).onLoginSuccess(mAuthData.toString());
    }

    @Test
    public void signInWithEmailInvalidEmailAddress() {
        result = Util.error_message_email_issue;
        doReturn(Observable.error(new FirebaseException(result)))
                .when(mMockDataManager)
                .authWithPassword("a@b.com", "password");
        mLoginPresenter.signinWithEmail("a@b.com", "password");
        verify(mMockLoginMvpView).showEmailError();
        verify(mMockLoginMvpView, never()).onLoginSuccess(result);
    }

    @Test
    public void signInWithEmailInvalidPassword() {
        result = Util.error_message_wrong_password;
        doReturn(Observable.error(new FirebaseException(result)))
                .when(mMockDataManager)
                .authWithPassword("a@b.com", "password");
        mLoginPresenter.signinWithEmail("a@b.com", "password");
        verify(mMockLoginMvpView).showPasswordError();
        verify(mMockLoginMvpView, never()).onLoginSuccess(result);
    }

    @Test
    public void signInWithEmailGeneralError() {
        result = Util.error_message_default;
        doReturn(Observable.error(new FirebaseException(result)))
                .when(mMockDataManager)
                .authWithPassword("a@b.com", "password");
        mLoginPresenter.signinWithEmail("a@b.com", "password");
        verify(mMockLoginMvpView).showGeneralError(result);
        verify(mMockLoginMvpView, never()).onLoginSuccess(result);
    }

    @Test
    public void alreadyAuthenticated() {
        doReturn(Observable.just(mAuthData))
                .when(mMockDataManager)
                .checkAuthState();
        mLoginPresenter.checkAuthState();
        verify(mMockLoginMvpView).launchMainActivity();
    }
}
