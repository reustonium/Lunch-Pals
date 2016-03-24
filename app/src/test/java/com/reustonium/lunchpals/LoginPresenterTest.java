package com.reustonium.lunchpals;

import com.reustonium.lunchpals.data.DataManager;
import com.reustonium.lunchpals.data.model.LoginResult;
import com.reustonium.lunchpals.data.remote.Util;
import com.reustonium.lunchpals.ui.login.LoginMvpView;
import com.reustonium.lunchpals.ui.login.LoginPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock LoginMvpView mMockLoginMvpView;
    @Mock DataManager mMockDataManager;
    private LoginPresenter mLoginPresenter;
    LoginResult result;

    @Before
    public void setup() {
        mLoginPresenter = new LoginPresenter(mMockDataManager);
        mLoginPresenter.attachView(mMockLoginMvpView);
        result = new LoginResult();
    }

    @After
    public void tearDown() {
        mLoginPresenter.detachView();
    }

    @Test
    public void signInWithEmailSuccess() {
        result.encodedEmail = "a@b.com";
        when(mMockDataManager.signinWithEmail("a@b.com", "password")).thenReturn(result);
        mLoginPresenter.signinWithEmail("a@b.com", "password");
        verify(mMockLoginMvpView).onLoginSuccess(result);
    }

    @Test
    public void signInWithEmailInvalidEmailAddress() {
        result.error = Util.error_message_email_issue;
        when(mMockDataManager.signinWithEmail("a@b.com", "password")).thenReturn(result);
        mLoginPresenter.signinWithEmail("a@b.com", "password");
        verify(mMockLoginMvpView).showEmailError();
        verify(mMockLoginMvpView, never()).onLoginSuccess(result);
    }

    @Test
    public void signInWithEmailInvalidPassword() {
        result.error = Util.error_message_wrong_password;
        when(mMockDataManager.signinWithEmail("a@b.com", "password")).thenReturn(result);
        mLoginPresenter.signinWithEmail("a@b.com", "password");
        verify(mMockLoginMvpView).showPasswordError();
        verify(mMockLoginMvpView, never()).onLoginSuccess(result);
    }

    @Test
    public void signInWithEmailGeneralError() {
        result.error = Util.error_message_default;
        when(mMockDataManager.signinWithEmail("a@b.com", "password")).thenReturn(result);
        mLoginPresenter.signinWithEmail("a@b.com", "password");
        verify(mMockLoginMvpView).showGeneralError(result.error);
        verify(mMockLoginMvpView, never()).onLoginSuccess(result);
    }
}
