package com.apps.pixabayapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.apps.pixabayapp.data.api.ApiHelper
import com.apps.pixabayapp.data.model.PhotoDataModel
import com.apps.pixabayapp.data.repository.PicsRepository
import com.apps.pixabayapp.ui.home.viewmodel.HomeViewModel
import com.apps.pixabayapp.utils.Resource
import com.apps.pixabayapp.utils.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeTest : BaseTest() {


    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: PicsRepository

    @Mock
    private lateinit var helper: ApiHelper

    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<PhotoDataModel>>

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = Mockito.spy(HomeViewModel(repository))
    }

    @After
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun test_PixaBayRepos_noQuery() = testCoroutineDispatcher.runBlockingTest {
        val searchQuery: String = ""
        val page: Int = 1

        // Trigger
        viewModel.fetchPhotos(searchQuery, page)

        // Validation
        Mockito.verify(helper, Mockito.never()).fetchPhotos(searchQuery, page)
    }

    @Test
    fun test_PixaBayRepos_success_query() = testCoroutineDispatcher.runBlockingTest {
        val searchQuery: String = "apple"
        val page: Int = 1

        // Trigger
        val data = viewModel.fetchPhotos(searchQuery, page)

        // Validation
        verify(viewModel, Mockito.times(1)).fetchPhotos(searchQuery, page)
    }

    @Test
    fun test_PixaBayRepos_success_query_load_page() = testCoroutineDispatcher.runBlockingTest {
        val searchQuery: String = "apple"
        val page: Int = 1

        // Trigger
        viewModel.fetchPhotos(searchQuery, page)


        // Validation
        verify(viewModel, Mockito.times(1)).fetchPhotos(searchQuery, page)

        //paginate
        viewModel.fetchPhotos(searchQuery, page)
        // Validation
        verify(viewModel, Mockito.times(2)).fetchPhotos(searchQuery, page)
    }

    @Test
    fun test_PixaBayRepos_success_query_verify_data() = testCoroutineDispatcher.runBlockingTest {
        val searchQuery: String = "apple"
        val page: Int = 1

        // Trigger
        viewModel.fetchPhotos(searchQuery, page)

    }

    @Test
    fun test_PixaBayRepos_success_query_verify() = testCoroutineDispatcher.runBlockingTest {
        val searchQuery: String = "apple"
        val page: Int = 1

        val data = viewModel.fetchPhotos(searchQuery, page)
        Assert.assertEquals(Status.SUCCESS, viewModel.getUsers().value?.status)

    }

    @Test
    fun test_PixaBayRepos_fail_query_verify() = testCoroutineDispatcher.runBlockingTest {
        val searchQuery: String = "nwcys"
        val page: Int = 1

        // Mockito.`when`(repository.fetchPhotos(searchQuery, page)).thenReturn(getEmptyResponse())

        // Trigger
        val data = viewModel.fetchPhotos(searchQuery, page)
        Assert.assertEquals(Status.SUCCESS, viewModel.getUsers().value?.status)
    }


    @Test
    fun test_PixaBay_Repos_observers_success() {
        testCoroutineRule.runBlockingTest {
            doReturn(PhotoDataModel())
                .`when`(helper)
                .fetchPhotos("", 1)
            val viewModel = HomeViewModel(helper)
            viewModel.fetchPhotos("", 1)
            viewModel.getUsers().observeForever(apiUsersObserver)
            verify(helper).fetchPhotos("", 1)
            verify(apiUsersObserver).onChanged(Resource.success(PhotoDataModel()))
            viewModel.getUsers().removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun test_PixaBay_Repos_response_success() {
        testCoroutineRule.runBlockingTest {
            doReturn(getSuccessResponse())
                .`when`(helper)
                .fetchPhotos("", 1)
            val viewModel = HomeViewModel(helper)
            viewModel.fetchPhotos("", 1)
            viewModel.getUsers().observeForever(apiUsersObserver)
            verify(helper).fetchPhotos("", 1)
            verify(apiUsersObserver).onChanged(Resource.success(getSuccessResponse()))
            Assert.assertEquals(2, viewModel.getUsers().value?.data?.hits?.size)
            viewModel.getUsers().removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun test_PixaBay_Repos_response_empty() {
        testCoroutineRule.runBlockingTest {
            doReturn(getEmptyResponse())
                .`when`(helper)
                .fetchPhotos("", 1)
            val viewModel = HomeViewModel(helper)
            viewModel.fetchPhotos("", 1)
            viewModel.getUsers().observeForever(apiUsersObserver)
            verify(helper).fetchPhotos("", 1)
            verify(apiUsersObserver).onChanged(Resource.success(getEmptyResponse()))
            Assert.assertEquals(0, viewModel.getUsers().value?.data?.hits?.size)
            viewModel.getUsers().removeObserver(apiUsersObserver)
        }
    }

    /*@Test
    fun test_PixaBay_Repos_response_exception() {
        testCoroutineRule.runBlockingTest {
            doThrow(RuntimeException("errorMessage"))
                .`when`(helper)
                .fetchPhotos("", 1)
            val viewModel = HomeViewModel(helper)
            viewModel.fetchPhotos("", 1)
            viewModel.getUsers().observeForever(apiUsersObserver)
            verify(helper).fetchPhotos("",1)
            verify(apiUsersObserver).onChanged(
                Resource.error(
                    null,
                    RuntimeException("errorMessage").toString()

                )
            )
            viewModel.getUsers().removeObserver(apiUsersObserver)
        }
    }*/

}
