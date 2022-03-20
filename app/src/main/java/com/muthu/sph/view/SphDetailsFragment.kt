package com.muthu.sph.view

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.muthu.sph.databinding.FragmentSphDetailsBinding
import com.muthu.sph.model.Records
import com.muthu.sph.viewmodel.SphListViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.muthu.sph.model.ListDataModel
import com.muthu.sph.view.adapter.ViewAdapter

class SphDetailsFragment : Fragment() {

    private lateinit var fragmentSphDetailsBinding: FragmentSphDetailsBinding
    private val listViewModel: SphListViewModel by activityViewModels()
    lateinit var listWholeData: List<Pair<String, List<Records>>>
    var selectedPosition: Int = 0
    lateinit var listDataModel: ListDataModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSphDetailsBinding = FragmentSphDetailsBinding.inflate(inflater, container, false)
        return fragmentSphDetailsBinding.apply {
            arguments?.let {
                selectedPosition = SphDetailsFragmentArgs.fromBundle(it).position
                listDataModel = SphDetailsFragmentArgs.fromBundle(it).listResponse
            }
            listViewModel.yearWiseListData.observe(viewLifecycleOwner, dataListObserver)
            getData()
        }.root
    }

    private fun getData() {
        listViewModel.groupDataByYear(listDataModel)
    }

    private val dataListObserver = Observer<List<Pair<String, List<Records>>>> { list ->
        listWholeData = list
        updateViewPagerList(listWholeData)
    }

    private fun updateViewPagerList(records: List<Pair<String, List<Records>>>) {
        val adapter = ViewAdapter(records)
        fragmentSphDetailsBinding.apply {
            this.viewpager.adapter = adapter
            this.viewpager.setCurrentItem(selectedPosition, false)
        }
    }
}