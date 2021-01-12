package com.project.scheduleappui.menu.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.scheduleappui.R
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        val listHome = listOf(
                Dashboard(image = R.drawable.bg_agenda1, agenda = "Hotel Cavana - Meeting with client from Russia", time = "07.00 - 07.30"),
                Dashboard(image = R.drawable.bg_agenda2, agenda = "TA Caffe - Market potential at Tulungagung", time = "08.00 - 09.00"),
                Dashboard(image = R.drawable.bg_agenda3, agenda = "Office - Internal project review & discussion", time = "09.00 - 09.30")
        )

        val homeAdapter = DashboardAdapter(listHome)

        recyclerview_dashboard.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = homeAdapter
        }
    }
}