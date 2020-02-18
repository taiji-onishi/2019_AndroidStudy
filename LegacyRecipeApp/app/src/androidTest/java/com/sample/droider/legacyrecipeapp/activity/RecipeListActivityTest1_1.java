package com.sample.droider.legacyrecipeapp.activity;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.recyclerview.widget.RecyclerView;
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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest1_1 {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void RecyclerView内の画像表示確認() throws Exception{
        //　通信結果を待つ
        Thread.sleep(1000);

        onView(allOf(isDescendantOfA(withItemViewAtPosition(withId(R.id.recycler), 0)), withId(R.id.image_main)))
                .check(matches(isDisplayed()));

        onView(allOf(isDescendantOfA(withItemViewAtPosition(withId(R.id.recycler), 0)), withId(R.id.text_recommend)))
                .check(matches(withText("おすすめ")));

        onView(allOf(isDescendantOfA(withItemViewAtPosition(withId(R.id.recycler), 1)), withId(R.id.text_recipe_name)))
                .check(matches(withText("基本の豚の角煮")));


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




    //　RecyclerView内を見るメソッド

    /**
     * <p>
     * Returns a matcher that matches an item view at the given position
     * in the RecyclerView matched by {@code recyclerViewMatcher}.
     * </p>
     * <p>
     * If the item view at the given position is not laid out,
     * the matcher returned by this method will not match anything.
     * Call { [atmark] link android.support.test.espresso.contrib.RecyclerViewActions#scrollToPosition(int)}
     * with the same position prior to calling this method
     * in order to ensure that the item view is laid out.
     * </p>
     * <pre><code>
     *     onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition(position));
     * </code></pre>
     *
     * @param recyclerViewMatcher a matcher for RecyclerView containing the item view.
     * @param position            position of the item view in RecyclerView.
     * @return a matcher that matches an item view at the given position.
     */
    public static Matcher<View> withItemViewAtPosition(final Matcher<View> recyclerViewMatcher, final int position) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                if (!(parent instanceof RecyclerView) || !recyclerViewMatcher.matches(parent)) {
                    return false;
                }
                RecyclerView.ViewHolder viewHolder = ((RecyclerView) parent).findViewHolderForAdapterPosition(position);
                return viewHolder != null && viewHolder.itemView.equals(view);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Item view at position " + position + " in recycler view ");
                recyclerViewMatcher.describeTo(description);
            }
        };
    }
}
