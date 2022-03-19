package com.muthu.sph.view.adapter

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muthu.sph.databinding.ItemDataBinding
import com.muthu.sph.model.ListDataModel
import com.muthu.sph.model.Records
import com.muthu.sph.util.roundTo
import kotlin.math.roundToInt

/**
 * a class to handle [RecyclerView] adapter
 * data we got from [ListDataModel]
 */
class SphListAdapter(private val listOfYearsAndData: List<Pair<String, List<Records>>>) :
    RecyclerView.Adapter<SphListAdapter.SphViewHolder>() {


    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of item_data.xml
    inner class SphViewHolder(val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root)

    // inside the onCreateViewHolder inflate the view of ItemDataBinding
    // and return new SphViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SphViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SphViewHolder(binding)
    }

    //    bind the items with each item
    override fun onBindViewHolder(holder: SphViewHolder, position: Int) {
        var usedDataInYear = 0.0
        listOfYearsAndData[position].second.forEach { unit ->
            d("mmm ", "${unit.volumeOfMobileData}")

            usedDataInYear += unit.volumeOfMobileData?.toDouble()!!
        }
        with(holder) {
            binding.apply {
                tvYear.text = listOfYearsAndData[position].first
                tvValueMobileData.text = "${usedDataInYear.roundTo()}"
            }
        }
    }

    //return the size of mobile Data
    override fun getItemCount(): Int {
        return listOfYearsAndData.size
    }

}