package com.muthu.sph.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.muthu.sph.databinding.FragmentSphListBinding
import com.muthu.sph.model.Records
import com.muthu.sph.model.SphError
import com.muthu.sph.util.checkInternetAccess
import com.muthu.sph.view.adapter.SphListAdapter
import com.muthu.sph.viewmodel.SphListViewModel

/**
 * [SphListFragment] is the main listing page and binding with the [SphListViewModel]
 * [SphListAdapter] used to load the list of data that we got from API
 * ViewBinding has been implemented
 */
class SphListFragment : BaseFragment() {

    private lateinit var listViewModel: SphListViewModel
    lateinit var viewBinding: FragmentSphListBinding

    private val resourceId = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f"

    //adapter
    lateinit var sphListAdapter: SphListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentSphListBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init listViewModel
        listViewModel = ViewModelProviders.of(this).get(SphListViewModel::class.java)
        listViewModel.apply {
            yearWiseListData.observe(viewLifecycleOwner, dataListObserver)
            loading.observe(viewLifecycleOwner, loadingIndicatorObserver)
            loadError.observe(viewLifecycleOwner, errorDataObserver)

            //check internet access is avail or not
            //if avail then just load the data
            //if not get data from local prefs
            //if the app is installed 1st time &
            // no internet then there is no data will be shown
            if (context?.checkInternetAccess() == true) {
                getDataList(resourceId)
            } else {
                getDataFromPrefs()
            }

        }
    }

    //observers
    private val dataListObserver = Observer<List<Pair<String, List<Records>>>> { list ->
        list?.let {
            //set up recyclerview & it's adapter
            viewBinding.rvDataPerYear.apply {
                layoutManager = LinearLayoutManager(context)
                if (adapter == null) {
                    sphListAdapter = SphListAdapter(it, listViewModel.listDataModel)
                    adapter = sphListAdapter
                }

                visibility = View.VISIBLE
                viewBinding.tvErrorNoData.visibility = View.GONE
            }
        }
    }

    /**
     * show/hide loading indicator
     */
    private val loadingIndicatorObserver = Observer<Boolean> { isLoading ->
        if (isLoading) {
            viewBinding.apply {
                pbLoading.visibility = View.VISIBLE
                tvErrorNoData.visibility = View.GONE
                rvDataPerYear.visibility = View.GONE
            }
        } else {
            viewBinding.pbLoading.visibility = View.GONE
        }

    }

    /**
     * show/hide error detail view
     */
    private val errorDataObserver = Observer<SphError> { sphError ->
        if (sphError.isError) {
            viewBinding.apply {
                pbLoading.visibility = View.GONE
                tvErrorNoData.visibility = View.VISIBLE
                tvErrorNoData.text = sphError.message
                rvDataPerYear.visibility = View.GONE
            }

        }

    }


    /**
     * screen view for log
     */
    override fun getScreenNameForAnalytics(): String {
        return "SphListFragment";
    }

    override fun itemThatUserCurrentlyViewing(): Int? {
        return null
    }


}