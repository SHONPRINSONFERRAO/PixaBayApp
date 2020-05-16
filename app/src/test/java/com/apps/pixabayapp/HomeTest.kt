package com.apps.pixabayapp

import android.util.Log
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
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeTest {


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
        Mockito.verify(viewModel, Mockito.times(1)).fetchPhotos(searchQuery, page)
    }

    @Test
    fun test_PixaBayRepos_success_query_load_page() = testCoroutineDispatcher.runBlockingTest {
        val searchQuery: String = "apple"
        val page: Int = 1

        // Trigger
        viewModel.fetchPhotos(searchQuery, page)


        // Validation
        Mockito.verify(viewModel, Mockito.times(1)).fetchPhotos(searchQuery, page)

        //paginate
        viewModel.fetchPhotos(searchQuery, page)
        // Validation
        Mockito.verify(viewModel, Mockito.times(2)).fetchPhotos(searchQuery, page)
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

       // Mockito.`when`(repository.fetchPhotos(searchQuery, page)).thenReturn(getSuccessResponse())

        /* val response =
             Mockito.mock(Response::class.java)
         val searchResponse: PhotoDataModel =
             Mockito.mock<PhotoDataModel>(PhotoDataModel::class.java)
         Mockito.doReturn(true).`when`(response).isSuccessful
         Mockito.doReturn(searchResponse).`when`(response).body()*/

        // Trigger
        val data = viewModel.fetchPhotos(searchQuery, page)
        Log.i("data", data.toString())
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

}
