package com.muthu.sph.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.muthu.sph.databinding.FragmentSphListBinding
import com.muthu.sph.model.Records
import com.muthu.sph.model.SphError
import com.muthu.sph.view.adapter.SphListAdapter
import com.muthu.sph.viewmodel.SphListViewModel
import kotlinx.android.synthetic.main.fragment_sph_list.*

class SphListFragment : Fragment() {

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
            getDataList(resourceId)
        }
    }


    //observers
    private val dataListObserver = Observer<List<Pair<String, List<Records>>>> { list ->
        list?.let {
            //set up recyclerview & it's adapter
            viewBinding.rvDataPerYear.apply {
                layoutManager = LinearLayoutManager(context)
                sphListAdapter = SphListAdapter(it,listViewModel.listDataModel)
                adapter = sphListAdapter

                visibility = View.VISIBLE
                tv_error_no_data.visibility = View.GONE
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

}