package com.picpay.desafio.android.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.api.PicPayService
import com.picpay.desafio.android.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BaseRepositoryTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val service = mock<PicPayService>()

    private val repository = BaseRepositoryImpl(service)

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
    fun whenGetUsers_shouldReturnListOfUsers() = testCoroutineDispatcher.runBlockingTest {
        val mockedListUser = mockedList()

        whenever(service.getUsers()).thenReturn(mockedList())
        val result = repository.getUsers()

        assertEquals(mockedListUser, result)
    }

    private fun mockedList(): List<User> {
        return listOf(
            User("https://randomuser.me/api/portraits/women/37.jpg", "Marina Coelho", 1001, "@marina.coelho"),
            User("https://randomuser.me/api/portraits/men/3.jpg", "André Castro", 1018, "@andre.castro")
        )
    }
}