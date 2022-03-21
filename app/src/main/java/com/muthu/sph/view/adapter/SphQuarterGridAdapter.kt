package com.muthu.sph.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muthu.sph.databinding.ItemQuarterViewBinding
import com.muthu.sph.model.Records
import com.muthu.sph.util.roundOff
import com.muthu.sph.util.toSphString

class SphQuarterGridAdapter(private val listOfQuarterData: List<Records>) :
    RecyclerView.Adapter<SphQuarterGridAdapter.SphQuarterGridVH>() {


    inner class SphQuarterGridVH(val binding: ItemQuarterViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SphQuarterGridVH {
        val binding =
            ItemQuarterViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SphQuarterGridVH(binding)
    }

    override fun onBindViewHolder(holder: SphQuarterGridVH, position: Int) {

        with(holder) {
            binding.apply {
                with(listOfQuarterData[position]) {
                    tvQuarterName.text = quarter?.split("-")?.get(1) ?: "N/A"
                    tvQuarterDataUsage.text =
                        volumeOfMobileData?.toDouble()?.roundOff()?.toSphString() ?: "N/A"
                }

            }
        }


    }

    override fun getItemCount(): Int {
        return listOfQuarterData.size
    }

}