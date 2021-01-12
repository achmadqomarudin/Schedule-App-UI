package com.project.scheduleappui.menu.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.scheduleappui.R
import kotlinx.android.synthetic.main.item_home.view.*

class HomeAdapter(private val home: List<Home>) : RecyclerView.Adapter<HomeHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): HomeHolder {
        return HomeHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_home, viewGroup, false))
    }

    override fun getItemCount(): Int = home.size

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.bindHero(home[position])
    }
}

class HomeHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvTag       = view.text_tag
    private val tvSchedule  = view.text_schedule
    private val tvTime      = view.text_schedule_time

    fun bindHero(home: Home) {
        tvTag.text      = home.tag
        tvSchedule.text = home.schedule
        tvTime.text     = home.time
    }
}