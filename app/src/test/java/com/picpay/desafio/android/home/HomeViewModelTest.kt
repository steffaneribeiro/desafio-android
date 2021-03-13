package com.picpay.desafio.android.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.repository.BaseRepository
import com.picpay.desafio.android.util.BaseModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val useCase: BaseRepository = mock()
    private val observerLiveData : Observer<BaseModelState> = mock()
    private lateinit var viewModel: HomeViewModel
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun whenStartViewModel_shouldCallGetUsers() = testCoroutineDispatcher.runBlockingTest {
        whenever(useCase.getUsers()).thenReturn(mockedList())
        viewModel = HomeViewModel(useCase)
        viewModel.user.observeForever(observerLiveData)
        verify(useCase).getUsers()
    }

    @Test
    fun whenStartViewModelWithSuccess_shouldReturnListOfUsers() = testCoroutineDispatcher.runBlockingTest {
        whenever(useCase.getUsers()).thenReturn(mockedList())

        viewModel = HomeViewModel(useCase)
        viewModel.user.observeForever(observerLiveData)
        viewModel.user

        verify(observerLiveData).onChanged(BaseModelState.success(mockedList()))
    }

    @Test
    fun whenStartViewModelWithError_shouldReturnErrorMessage() = testCoroutineDispatcher.runBlockingTest {
        viewModel = HomeViewModel(useCase)
        viewModel.user.observeForever(observerLiveData)

        viewModel.user

        val value = viewModel.user.value ?: error("No value for viewModel")

        verify(observerLiveData).onChanged(value.error?.let { BaseModelState.error(it) })
    }

    private fun mockedList(): List<User> {
        return listOf(
            User("https://randomuser.me/api/portraits/women/37.jpg", "Marina Coelho", 1001, "@marina.coelho"),
            User("https://randomuser.me/api/portraits/men/3.jpg", "André Castro", 1018, "@andre.castro"))
    }
}