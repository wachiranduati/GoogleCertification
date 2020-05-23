package nduati.soc.googlecertification


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest3 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest3() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.toast_button), withText("Toast"),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.toast_button), withText("Toast"),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.snackbar_button), withText("SnackBar"),
                isDisplayed()
            )
        )
        appCompatButton3.perform(click())

//        pressBack()
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
