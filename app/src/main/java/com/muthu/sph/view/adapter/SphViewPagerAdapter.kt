package com.muthu.sph.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muthu.sph.R
import com.muthu.sph.databinding.ItemViewPagerBinding
import com.muthu.sph.model.Records
import com.muthu.sph.util.roundOff
import com.muthu.sph.util.toSphString


class SphViewPagerAdapter(
    private val list: List<Pair<String, List<Records>>>
) : RecyclerView.Adapter<SphViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.apply {
                    txtYear.text = txtYear.context.getString(
                        R.string.str_statistics_val,
                        first,
                    )
                    var usedDataInYear = 0.0
                    second.forEach { unit ->
                        usedDataInYear += unit.volumeOfMobileData?.toDouble()!!
                    }
                    txtTotalDataUsed.text = (
                            usedDataInYear.roundOff().toSphString()
                            )

                    //dynamic grid layout
                    val layoutManager = GridLayoutManager(rvQuarterDetails.context, 2)
                    this.rvQuarterDetails.layoutManager = layoutManager
                    val adapter = SphQuarterGridAdapter(second)

                    this.rvQuarterDetails.adapter = adapter

                }
            }
        }
    }


    override fun getItemId(position: Int): Long = position.toLong()


    override fun getItemViewType(position: Int): Int = position


    override fun getItemCount(): Int = list.size


    inner class ViewHolder(val binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root)
}