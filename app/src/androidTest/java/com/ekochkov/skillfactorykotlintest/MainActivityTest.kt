import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.ekochkov.skillfactorykotlintest.FilmItemHolder
import com.ekochkov.skillfactorykotlintest.R
import com.ekochkov.skillfactorykotlintest.MainActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var mActivityRule: ActivityScenarioRule<MainActivity?>? = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun recyclerViewShouldBeAttached() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<FilmItemHolder>(0, click()))
    }

    @Test
    fun searchViewShouldBeAbleToInputText() {
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
        val testText = "король"
        onView(withId(R.id.search_view)).perform(typeSearchViewText(testText))
    }

    @Test
    fun allBottomMenuButtonsShouldWork() {
        onView(withId(R.id.later)).perform(click())
        onView(withId(R.id.later_container)).check(matches(isDisplayed()))
        onView(withId(R.id.fav)).perform(click())
        onView(withId(R.id.fav_container)).check(matches(isDisplayed()))
        onView(withId(R.id.compile)).perform(click())
        onView(withId(R.id.compile_container)).check(matches(isDisplayed()))
        onView(withId(R.id.home)).perform(click())
        onView(withId(R.id.home_container)).check(matches(isDisplayed()))
    }

    @Test
    fun filmPageMustOpenedByClickOnFilmItemHolder() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<FilmItemHolder>(0, click()))
        onView(withId(R.id.film_page_container)).check(matches(isDisplayed()))
    }

    @Test
    fun favFabShouldBeClickable() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<FilmItemHolder>(0, click()))
        onView(withId(R.id.film_page_container)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_fav)).check(matches(isClickable()))
    }

    @Test
    fun UIAutomatorTest() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        Thread.sleep(1000)
        device.click(100, 1700)
        Thread.sleep(1000)
        device.pressBack()
        Thread.sleep(1000)
    }

    private fun typeSearchViewText(text: String?): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                //Ensure that only apply if it is a SearchView and if it is visible.
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun getDescription(): String {
                return "Change view text"
            }

            override fun perform(uiController: UiController?, view: View) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }

}