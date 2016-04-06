package com.reustonium.lunchpals.ui.main;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.reustonium.lunchpals.R;
import com.reustonium.lunchpals.test.common.TestDataFactory;
import com.reustonium.lunchpals.test.common.rules.TestComponentRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());

    public final ActivityTestRule<MainActivity> main =
            new ActivityTestRule<MainActivity>(MainActivity.class, false, false) {
                @Override
                protected Intent getActivityIntent() {
                    // Override the default intent so we pass a false flag for syncing so it doesn't
                    // start a sync service in the background that would affect  the behaviour of
                    // this test.
                    return MainActivity.getStartIntent(
                            InstrumentationRegistry.getTargetContext());
                }
            };

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void testOnCreate() throws Exception {
        main.launchActivity(null);

        //Toolbar get's setup and displayed
        CharSequence title = InstrumentationRegistry.getTargetContext()
                .getString(R.string.app_name);
        matchToolbarTitle(title);

        //RecyclerView get's setup and displayed
        onView(withId(R.id.activity_main_recycler_pal_list)).check(matches(isDisplayed()));

        //mainPresenter attaches the view
        //mainPresenter loads pals
    }

    @Test
    public void testShowPals() throws Exception {
        //Pals are added to the Adapter
        //RecyclerView updates
        List<String> testPals = TestDataFactory.makePalsList(5);
        when(component.getMockDataManager().getPals())
                .thenReturn(testPals);

        main.launchActivity(null);

        int position = 0;
        for (String s : testPals) {
            onView(withId(R.id.activity_main_recycler_pal_list))
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
        main.launchActivity(null);
    }

    @Test
    public void testShowError() throws Exception {
        //TODO
        //Show an error
        main.launchActivity(null);
    }

    @Test
    public void testOnPalClicked() throws Exception {
        //ProfileActivity Intent get's launched
        main.launchActivity(null);
        onView(withId(R.id.activity_main_toolbar)).perform(click());
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