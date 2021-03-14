package com.picpay.desafio.android.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.picpay.desafio.android.R
import com.picpay.desafio.android.util.ToastMatchers
import com.picpay.desafio.android.util.RecyclerViewMatchers.checkRecyclerViewItem
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.app.networkModuleTest
import com.picpay.desafio.android.ui.home.HomeFragment
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import java.util.concurrent.TimeUnit


class HomeFragmentTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val server = MockWebServer()

    private fun initFragment() = launchFragmentInContainer <HomeFragment>()

    @Before
    fun setup() {
        server.start(serverPort)
        loadKoinModules(
            networkModuleTest(
                server.url(
                    ""
                ).toString()
            )
        )
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun whenInitFragment_shouldDisplayTitle(){
        val expectedTitle = context.getString(R.string.title)
        initFragment().apply {
            onView(ViewMatchers.withId(R.id.title)).check(
                matches(
                    Matchers.allOf(
                        isDisplayed(),
                        withText(expectedTitle)
                    )
                )
            )
        }
    }

    @Test
    fun whenInitFragment_shouldDisplayProgressBar() {
        initFragment().apply {
            onView(ViewMatchers.withId(R.id.user_list_progress_bar)).check(
                matches(
                    isDisplayed()
                )
            )
        }
    }

    @Test
    fun whenInitFragment_shouldDisplayToastOnError() {
        mockResponseError()

        val errorMessage = context.getString(R.string.error)
        initFragment().apply {
            Thread.sleep(500)
            onView(withText(errorMessage)).inRoot(ToastMatchers())
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun whenInitFragment_shouldDisplayListItem() {
        mockResponseSuccess()
        initFragment().apply {
            Thread.sleep(500)
            checkRecyclerViewItem(R.id.recyclerView, 0, withText("@eduardo.santos"))
        }
    }

    companion object {
        private const val serverPort = 8080
    }

    private fun mockResponseSuccess(delay: Long = 0) {
        val response = listOf(
            User(
            "https://randomuser.me/api/portraits/men/9.jpg",
            "Eduardo Santos",
            1001,
            "@eduardo.santos")
        )
        mockResponse(Gson().toJson(response), 200, delay)
    }

    private fun mockResponseError() {
        mockResponse("", 400)
    }

    private fun mockResponse(responseJson: String, code: Int, delay: Long = 0) {
        val mockResponse = MockResponse()
            .setResponseCode(code)
            .setBody(responseJson)
            .setBodyDelay(delay, TimeUnit.MILLISECONDS)
        server.enqueue(mockResponse)
    }
}