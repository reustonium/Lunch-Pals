package com.reustonium.lunchpals.pals;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.text.TextUtils;
import android.view.View;

import com.reustonium.lunchpals.R;

import junit.framework.Assert;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Andrew on 2/5/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PalsScreenTest {

    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(
                        isDescendantOfA(isAssignableFrom(RecyclerView.class)),
                        withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA RV with text " + itemText);
            }
        };
    }

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<PalsActivity> mPalsActivityTestRule =
            new ActivityTestRule<>(PalsActivity.class);

    @Test
    public void clickAddPalButton_opensAddPalUi() throws Exception {
        // Click on the add pal button
        onView(withId(R.id.fab_add_pal)).perform(click());

        // Check if the add pal screen is displayed
        //onView(withId(R.id.add_pal_title)).check(matches(isDisplayed()));

        //Check for Snackbar
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Launch Add Pal Activity")))
                .check(matches(isDisplayed()));
    }

//    @Test
//    public void addPalToPalsList() throws Exception {
//        String newPalTitle = "Espresso";
//        String newPalDescription = "UI testing for Android";
//
//        // Click on the add pal button
//        onView(withId(R.id.fab_add_pal)).perform(click());
//
//        // Add pal title and description
//        onView(withId(R.id.add_pal_title)).perform(typeText(newPalTitle)); // Type new pal title
//        onView(withId(R.id.add_pal_description)).perform(typeText(newPalDescription),
//                closeSoftKeyboard()); // Type new pal description and close the keyboard
//
//        // Save the pal
//        onView(withId(R.id.fab_add_pals)).perform(click());
//
//        // Scroll pals list to added pal, by finding its description
//        onView(withId(R.id.pals_list)).perform(
//                scrollTo(hasDescendant(withText(newPalDescription))));
//
//        // Verify pal is displayed on screen
//        onView(withItemText(newPalDescription)).check(matches(isDisplayed()));
//    }
}
