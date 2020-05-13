package com.apps.pixabayapp

import com.apps.pixabayapp.data.repository.PicsRepository
import com.apps.pixabayapp.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class HomeTest {
    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var repository: PicsRepository

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = Mockito.spy(HomeViewModel(repository));
    }

    @After
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun test_PixaBayRepos_noQuery() = testCoroutineDispatcher.runBlockingTest {
        val searchQuery: String = ""
        val page:  Int = 1

        // Trigger
        viewModel.getUsers(searchQuery, page)

        // Validation
        Mockito.verify(repository, Mockito.never()).getUsers(searchQuery, page)
    }

    @Test
    fun test_PixaBayRepos_success_query() = testCoroutineDispatcher.runBlockingTest {
        val searchQuery: String = "apple"
        val page:  Int = 1

        // Trigger
        val data = viewModel.getUsers(searchQuery, page)

        // Validation
        Mockito.verify(repository, Mockito.times(1)).getUsers(searchQuery,page)
    }

}