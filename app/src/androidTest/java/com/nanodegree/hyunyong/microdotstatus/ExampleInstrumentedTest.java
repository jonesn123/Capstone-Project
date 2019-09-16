package com.nanodegree.hyunyong.microdotstatus;

import android.view.KeyEvent;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.nanodegree.hyunyong.microdotstatus.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testMicrodot() {

        wait(5000);

        onView(withId(R.id.fab_add)).perform(ViewActions.click());

        wait(1000);
        onView(withId(R.id.fab_search)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click());

        wait(1000);
        onView(withId(R.id.action_search)).perform(ViewActions.click());

        onView(withId(R.id.search_src_text)).perform(typeText("New York")).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        wait(1000);

        onView(withId(R.id.search_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        wait(1000);
    }

    private void wait(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
