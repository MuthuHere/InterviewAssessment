package com.muthu.sph.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.muthu.sph.databinding.FragmentSphDetailsBinding
import com.muthu.sph.model.ListDataModel
import com.muthu.sph.model.Records
import com.muthu.sph.view.adapter.SphViewPagerAdapter
import com.muthu.sph.viewmodel.SphListViewModel

class SphDetailsFragment : BaseFragment() {

    private lateinit var fragmentSphDetailsBinding: FragmentSphDetailsBinding
    private val listViewModel: SphListViewModel by activityViewModels()
    private lateinit var listWholeData: List<Pair<String, List<Records>>>
    private var selectedPosition: Int = 0
    private lateinit var listDataModel: ListDataModel

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
            //observing the changes
            listViewModel.yearWiseListData.observe(viewLifecycleOwner, dataListObserver)
            groupData()
        }.root
    }

    /**
     * grouping the data back
     */
    private fun groupData() {
        listViewModel.groupDataByYear(listDataModel)
    }

    /**
     * listening the group data
     */
    private val dataListObserver = Observer<List<Pair<String, List<Records>>>> { list ->
        listWholeData = list
        updateViewPagerList(listWholeData)
    }

    /**
     * update the data to the viewPager
     * with the data
     */
    private fun updateViewPagerList(records: List<Pair<String, List<Records>>>) {
        val adapter = SphViewPagerAdapter(records)
        fragmentSphDetailsBinding.apply {
            this.viewpager.adapter = adapter
            //setting the position which user clicks
            this.viewpager.setCurrentItem(selectedPosition, false)
        }
    }

    /**
     * screen view for log
     */
    override fun getScreenNameForAnalytics(): String {
        return "SphDetailsFragment"
    }

    override fun itemThatUserCurrentlyViewing(): Int {
        return selectedPosition
    }

}