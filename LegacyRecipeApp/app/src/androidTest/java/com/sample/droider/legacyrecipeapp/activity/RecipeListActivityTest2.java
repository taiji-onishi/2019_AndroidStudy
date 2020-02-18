package com.sample.droider.legacyrecipeapp.activity;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.sample.droider.legacyrecipeapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest2 {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void 詳細に遷移後表示確認() {
        ViewInteraction cardView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recycler),
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        cardView.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.image_main),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.text_title), withText("材料"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        2),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("材料")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.text_material), withText("合いびき肉"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        3),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("合いびき肉")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.text_quantity), withText("200ｇ"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        3),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("200ｇ")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.text_quantity), withText("200ｇ"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        3),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("200ｇ")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
