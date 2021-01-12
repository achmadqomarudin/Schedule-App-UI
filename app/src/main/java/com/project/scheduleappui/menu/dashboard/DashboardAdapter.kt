package com.project.scheduleappui.menu.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.scheduleappui.R
import kotlinx.android.synthetic.main.item_dashboard.view.*
import kotlinx.android.synthetic.main.item_home.view.*

class DashboardAdapter(private val dashboard: List<Dashboard>) : RecyclerView.Adapter<DashboardHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): DashboardHolder {
        return DashboardHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_dashboard, viewGroup, false))
    }

    override fun getItemCount(): Int = dashboard.size

    override fun onBindViewHolder(holder: DashboardHolder, position: Int) {
        holder.bindHero(dashboard[position])
    }
}

class DashboardHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val imgAgenda = view.image_agenda
    private val tvAgenda  = view.text_agenda
    private val tvTime    = view.text_agenda_time

    fun bindHero(dashboard: Dashboard) {
        imgAgenda.setImageResource(dashboard.image)
        tvAgenda.text = dashboard.agenda
        tvTime.text   = dashboard.time
    }
}