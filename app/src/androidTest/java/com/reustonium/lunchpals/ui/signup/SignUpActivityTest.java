package com.reustonium.lunchpals.ui.signup;


import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.TextView;

import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.matchers.ErrorTextMatchers;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityTestRule = new ActivityTestRule<>(SignUpActivity.class);

    @Test
    public void signUpActivityTest() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.signup_username_edit_text),
                        withParent(allOf(withId(R.id.signup_username_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText.perform(scrollTo(), replaceText("Jimmy"));

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.signup_username_edit_text), withText("Jimmy"),
                        withParent(allOf(withId(R.id.signup_username_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText2.perform(pressImeActionButton());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.signup_email_edit_text),
                        withParent(allOf(withId(R.id.signup_email_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText3.perform(pressImeActionButton());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.signup_password_edit_text),
                        withParent(allOf(withId(R.id.signup_password_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText4.perform(pressImeActionButton());

        ViewInteraction textInputLayout = onView(
                allOf(withId(R.id.signup_email_layout), withText("Email Address"),
                        withParent(allOf(withId(R.id.signup_email_login_form),
                                withParent(withId(R.id.signup_form)))),
                        isDisplayed()));
        textInputLayout.check(ViewAssertions.matches(
                ErrorTextMatchers.withErrorText(Matchers.containsString("Missing Email"))
        ));

        ViewInteraction textInputLayout2 = onView(
                allOf(withId(R.id.signup_password_layout), withText("Password"),
                        withParent(allOf(withId(R.id.signup_email_login_form),
                                withParent(withId(R.id.signup_form)))),
                        isDisplayed()));
        textInputLayout2.check(matches(isDisplayed()));

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.signup_email_edit_text),
                        withParent(allOf(withId(R.id.signup_email_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText5.perform(scrollTo(), replaceText("a"));

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.signup_email_edit_text), withText("a"),
                        withParent(allOf(withId(R.id.signup_email_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText6.perform(pressImeActionButton());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.signup_password_edit_text),
                        withParent(allOf(withId(R.id.signup_password_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText7.perform(pressImeActionButton());

        ViewInteraction textInputLayout3 = onView(
                allOf(withId(R.id.signup_email_layout), withText("Email Address"),
                        withParent(allOf(withId(R.id.signup_email_login_form),
                                withParent(withId(R.id.signup_form)))),
                        isDisplayed()));
        textInputLayout3.check(ViewAssertions.matches(
                ErrorTextMatchers.withErrorText(Matchers.containsString("Missing Email"))
        ));
        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.signup_password_edit_text),
                        withParent(allOf(withId(R.id.signup_password_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText8.perform(scrollTo(), replaceText("aaaaaaaa"));

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.signup_password_edit_text), withText("aaaaaaaa"),
                        withParent(allOf(withId(R.id.signup_password_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText9.perform(pressImeActionButton());

        ViewInteraction textInputLayout4 = onView(
                allOf(withId(R.id.signup_password_layout), withText("Password"),
                        withParent(allOf(withId(R.id.signup_email_login_form),
                                withParent(withId(R.id.signup_form)))),
                        isDisplayed()));
        textInputLayout4.check(ViewAssertions.matches(
                ErrorTextMatchers.withErrorText(Matchers.containsString("Missing Email"))
        ));

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.signup_email_edit_text), withText("a"),
                        withParent(allOf(withId(R.id.signup_email_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText10.perform(scrollTo(), click());

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.signup_email_edit_text), withText("a"),
                        withParent(allOf(withId(R.id.signup_email_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText11.perform(scrollTo(), replaceText("a@b.com"));

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.signup_email_edit_text), withText("a@b.com"),
                        withParent(allOf(withId(R.id.signup_email_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText12.perform(pressImeActionButton());

        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.signup_password_edit_text), withText("aaaaaaaa"),
                        withParent(allOf(withId(R.id.signup_password_layout),
                                withParent(withId(R.id.signup_email_login_form)))),
                        isDisplayed()));
        textInputEditText13.perform(pressImeActionButton());

    }
}
