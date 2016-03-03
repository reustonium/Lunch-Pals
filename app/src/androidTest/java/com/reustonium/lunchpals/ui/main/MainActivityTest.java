package com.reustonium.lunchpals.ui.main;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.reustonium.lunchpals.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testOnCreate() throws Exception {


        //Toolbar get's setup and displayed
        CharSequence title = InstrumentationRegistry.getTargetContext()
                .getString(R.string.app_name);
        matchToolbarTitle(title);

        //RecyclerView get's setup and displayed
        onView(withId(R.id.recycler_pal_list)).check(matches(isDisplayed()));

        //mainPresenter attaches the view
        //mainPresenter loads pals
    }

    @Test
    public void testShowPals() throws Exception {
        //Pals are added to the Adapter
        //RecyclerView updates
        List<String> testPals = new ArrayList();
        testPals.add("Andy");
        testPals.add("Jimmy Jamm");
        testPals.add("Frankzilla");
        testPals.add("Mr. Ballooon Hands");

        int position = 0;
        for (String s : testPals) {
            onView(withId(R.id.recycler_pal_list))
                    .perform(RecyclerViewActions.scrollToPosition(position));
            String name = s;
            onView(withText(name))
                    .check(matches(isDisplayed()));
            position++;
        }
    }

    @Test
    public void testShowPalsEmpty() throws Exception {
        //TODO
        //Show an empty List warning
    }

    @Test
    public void testShowError() throws Exception {
        //TODO
        //Show an error
    }

    @Test
    public void testOnPalClicked() throws Exception {
        //ProfileActivity Intent get's launched
        onView(withId(R.id.toolbar)).perform(click());
    }

    private static ViewInteraction matchToolbarTitle(CharSequence title) {
        return onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(title))));
    }

    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }
            @Override public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }
}