package com.muthu.sph.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.muthu.sph.di.api.AppModule
import com.muthu.sph.di.viewmodel.DaggerViewModelComponent
import com.muthu.sph.model.ListDataModel
import com.muthu.sph.model.Records
import com.muthu.sph.model.SphError
import com.muthu.sph.service.ApiService
import com.muthu.sph.util.SharedPrefHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Class to maintain all business logic for [ListDataModel] List and
 * any logic needed by [Records] View to interact with data
 */
class SphListViewModel(application: Application) : AndroidViewModel(application) {

    constructor(application: Application, isTest: Boolean = true) : this(application) {
        isInjected = isTest
    }

    var isInjected = false
    lateinit var listDataModel: ListDataModel
    val yearWiseListData by lazy {
        MutableLiveData<List<Pair<String, List<Records>>>>()
    }
    val loadError by lazy {
        MutableLiveData<SphError>()
    }
    val loading by lazy {
        MutableLiveData<Boolean>()
    }


    private val disposable = CompositeDisposable()

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var prefs: SharedPrefHelper

    //inject the lateinits with dagger
    private fun inject() {
        DaggerViewModelComponent.builder()
            .appModule(AppModule(getApplication()))
            .build()
            .inject(this)
    }


    fun getDataFromPrefs(): Boolean {
        inject()
        return try {
            listDataModel = prefs.retrieveData()!!
            groupDataByYear(listDataModel)
            true
        } catch (e: Exception) {
            loadError.value = SphError(
                true,
                e.message
                    ?: "Sorry, Unable to connect the server. Check your internet connection & Try again later"
            )

            false
        }

    }

    // api service to get the data from server
    // the "resource_id" being passed as hardcoded value we should
    fun getDataList(resourceId: String) {
        //from view & test case
        inject()

        if (!yearWiseListData.value.isNullOrEmpty()) {
            return
        }
        loading.value = true
        disposable.add(
            apiService.getData(resourceId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                    object : DisposableSingleObserver<ListDataModel>() {
                        override fun onSuccess(t: ListDataModel) {
                            listDataModel = t
                            //store data for offline when run the real case
                            //for testing the view model no need to save data
                            if (!isInjected)
                                prefs.storeData(t)
                            groupDataByYear(listDataModel)
                        }

                        override fun onError(e: Throwable) {
                            loading.value = false
                            loadError.value = SphError(
                                true,
                                e.message
                                    ?: "Sorry, Something went wrong &unable to show the data at this moment."
                            )
                        }

                    },
                )
        )
    }

    //to show in our recyclerview it would be better restructure the data value as per our requirement
    //here i would like to change the response data to List<Pair<String, List<Records>>>
    //so that we can go with the year and its pair data(list of records in that particular year)
    fun groupDataByYear(listDataModel: ListDataModel) {
        loading.value = false
        //grouping and re structuring the data
        val listOfSortedData =
            listDataModel.result?.records?.groupBy { it -> it.quarter!!.split("-")[0] }?.toList()

        //checking before assign the data
        if (listOfSortedData.isNullOrEmpty()) {
            loadError.value = SphError(true, "Seems there is no data right for this record")
        } else {
            yearWiseListData.value = listOfSortedData
        }
    }


    // clear the CompositeDisposable when this viewModel's lifecycle gets clear
//    override fun onCleared() {
//        super.onCleared()
//        disposable.clear()
//    }
}