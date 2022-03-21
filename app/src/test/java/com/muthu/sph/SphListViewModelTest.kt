package com.muthu.sph

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.muthu.sph.di.viewmodel.DaggerViewModelComponent
import com.muthu.sph.model.ListDataModel
import com.muthu.sph.model.Records
import com.muthu.sph.model.Result
import com.muthu.sph.model.SphError
import com.muthu.sph.service.ApiService
import com.muthu.sph.viewmodel.SphListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class SphListViewModelTest {

    private val key = "DUMMY-RESOURCE-ID"
    private val validKey = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f"

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: ApiService

    private val application = Mockito.mock(Application::class.java)

    private val sphListViewModel = SphListViewModel(application, true)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val testComponent = DaggerViewModelComponent.builder()
            .apiModule(ApiModuleTest(apiService))
            .build()
            .inject(sphListViewModel)
    }

    @Test
    fun getDataListSuccess() {
        val records = Records(volumeOfMobileData="0.000384", quarter="2004-Q3", id=1)

        val testData = ListDataModel(null, null, null, null)

        val testSingle = Single.just(testData)

        Mockito.`when`(apiService.getData(validKey)).thenReturn(testSingle)
        sphListViewModel.getDataList(validKey)

        Assert.assertEquals(records, sphListViewModel.yearWiseListData.value?.get(0)?.second?.get(0))
        Assert.assertEquals(
            null,
            sphListViewModel.loadError.value
        )
        Assert.assertEquals(false, sphListViewModel.loading.value)
    }


    @Test
    fun getDataListFailure() {
        val testData = ListDataModel(null, null, null, null)

        val testSingle = Single.just(testData)

        Mockito.`when`(apiService.getData(key)).thenReturn(testSingle)
        sphListViewModel.getDataList(key)

        Assert.assertEquals(null, sphListViewModel.yearWiseListData.value)
        Assert.assertEquals(
            SphError(isError = true, message = "HTTP 404 Not Found"),
            sphListViewModel.loadError.value
        )
        Assert.assertEquals(false, sphListViewModel.loading.value)
    }


    @Before
    fun setupApiSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }

        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler ->
            immediate
        }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler ->
            immediate

        }
    }
}