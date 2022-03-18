package com.muthu.sph.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.muthu.sph.R
import kotlinx.android.synthetic.main.fragment_sph_list.*

class SphListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sph_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnList.setOnClickListener {
            val action = SphListFragmentDirections.toDetailPage()
            Navigation.findNavController(it).navigate(action)
        }
    }

}