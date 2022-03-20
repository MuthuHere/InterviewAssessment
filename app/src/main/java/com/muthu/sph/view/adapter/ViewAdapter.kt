package com.muthu.sph.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muthu.sph.databinding.ViewListViewBinding
import com.muthu.sph.model.Records

class ViewAdapter(
    private val list: List<Pair<String, List<Records>>>
) : RecyclerView.Adapter<ViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.txtYear.text = list[position].first
            }
        }
    }


    override fun getItemId(position: Int): Long = position.toLong()


    override fun getItemViewType(position: Int): Int = position


    override fun getItemCount(): Int = list.size


    inner class ViewHolder(val binding: ViewListViewBinding) :
        RecyclerView.ViewHolder(binding.root)
}