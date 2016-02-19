package com.reustonium.lunchpals;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import com.reustonium.lunchpals.data.model.Ribot;
import com.reustonium.lunchpals.test.common.TestDataFactory;
import com.reustonium.lunchpals.test.common.rules.TestComponentRule;
import com.reustonium.lunchpals.ui.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
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
                            InstrumentationRegistry.getTargetContext(), false);
                }
            };

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void listOfRibotsShows() {
        List<Ribot> testDataRibots = TestDataFactory.makeListRibots(20);
        when(component.getMockDataManager().getRibots())
                .thenReturn(Observable.just(testDataRibots));

        main.launchActivity(null);

        int position = 0;
        for (Ribot ribot : testDataRibots) {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.scrollToPosition(position));
            String name = String.format("%s %s", ribot.profile.name.first,
                    ribot.profile.name.last);
            onView(withText(name))
                    .check(matches(isDisplayed()));
            onView(withText(ribot.profile.email))
                    .check(matches(isDisplayed()));
            position++;
        }
    }


    @Test
    public void toolbarShow() {
        List<Ribot> testDataRibots = new ArrayList<>();
        when(component.getMockDataManager().getRibots())
                .thenReturn(Observable.just(testDataRibots));

        main.launchActivity(null);

        CharSequence title = "Lunch Pals";
        matchToolbarTitle(title);
    }

    @Test
    public void emptyRibotList() {
        List<Ribot> testDataRibots = new ArrayList<>();
        when(component.getMockDataManager().getRibots())
                .thenReturn(Observable.just(testDataRibots));

        main.launchActivity(null);

        onView(withText(R.string.empty_ribots)).inRoot(withDecorView(not(main.getActivity()
                .getWindow().getDecorView()))).check(matches(isDisplayed()));
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