package com.project.scheduleappui.menu.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import com.project.scheduleappui.R
import kotlinx.android.synthetic.main.calendar_item.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {
    private val calendar = Calendar.getInstance()
    private var currentMonth = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setDate()
        setSpinner(view)
    }

    private fun setData() {
        val listHome = listOf(
                Home(tag = "Developer", schedule = "Database Workshop - How to use SQLite Database in android", time = "08.00 - 09.00"),
                Home(tag = "Designer", schedule = "UI Workshop - Using gridview to make more beautiful design", time = "09.00 - 10.00"),
                Home(tag = "Marketing", schedule = "FB Ads - Strategy marketing for selling more products", time = "12.00 - 12.30")
        )

        val homeAdapter = HomeAdapter(listHome)

        recyclerview_home.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }
    }

    private fun setDate() {
        // set current date to calendar and current month to currentMonth variable
        calendar.time = Date()
        currentMonth = calendar[Calendar.MONTH]

        // calendar view manager is responsible for our displaying logic
        val myCalendarViewManager = object :
            CalendarViewManager {
            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean
            ): Int {
                // set date to calendar according to position where we are
                val cal = Calendar.getInstance()
                cal.time = date
                // if item is selected we return this layout items
                // in this example. monday, wednesday and friday will have special item views and other days
                // will be using basic item view
                return if (isSelected)
                    R.layout.selected_calendar_item
                else
                // here we return items which are not selected
                    R.layout.calendar_item

                // NOTE: if we don't want to do it this way, we can simply change color of background
                // in bindDataToCalendarView method
            }

            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean
            ) {
                // using this method we can bind data to calendar view
                // good practice is if all views in layout have same IDs in all item views
                holder.itemView.tv_date_calendar_item.text = DateUtils.getDayNumber(date)
                holder.itemView.tv_day_calendar_item.text = DateUtils.getDay3LettersName(date)

            }
        }

        // using calendar changes observer we can track changes in calendar
        val myCalendarChangesObserver = object :
            CalendarChangesObserver {
            // you can override more methods, in this example we need only this one
            @SuppressLint("SetTextI18n")
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                tvDate.text = "${DateUtils.getMonthName(date)} "
                super.whenSelectionChanged(isSelected, position, date)
            }
        }

        // selection manager is responsible for managing selection
        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                // set date to calendar according to position
                val cal = Calendar.getInstance()
                cal.time = date
                // in this example sunday and saturday can't be selected, others can
                return when (cal[Calendar.DAY_OF_WEEK]) {
                    Calendar.SATURDAY -> false
                    Calendar.SUNDAY -> false
                    else -> true
                }
            }
        }

        // here we init our calendar, also you can set more properties if you haven't specified in XML layout
        val singleRowCalendar = main_single_row_calendar.apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            setDates(getFutureDatesOfCurrentMonth())
            init()
        }

        btnRight.setOnClickListener {
            singleRowCalendar.setDates(getDatesOfNextMonth())
        }

        btnLeft.setOnClickListener {
            singleRowCalendar.setDates(getDatesOfPreviousMonth())
        }
    }

    private fun setSpinner(view: View) {
        val spinner: Spinner = view.findViewById(R.id.task_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                    R.array.task_array,
                    R.layout.item_spinner
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.item_spinner)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
        }
    }

    private fun getDatesOfNextMonth(): List<Date> {
        currentMonth++ // + because we want next month
        if (currentMonth == 12) {
            // we will switch to january of next year, when we reach last month of year
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] + 1)
            currentMonth = 0 // 0 == january
        }
        return getDates(mutableListOf())
    }

    private fun getDatesOfPreviousMonth(): List<Date> {
        currentMonth-- // - because we want previous month
        if (currentMonth == -1) {
            // we will switch to december of previous year, when we reach first month of year
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] - 1)
            currentMonth = 11 // 11 == december
        }
        return getDates(mutableListOf())
    }

    private fun getFutureDatesOfCurrentMonth(): List<Date> {
        // get all next dates of current month
        currentMonth = calendar[Calendar.MONTH]
        return getDates(mutableListOf())
    }

    private fun getDates(list: MutableList<Date>): List<Date> {
        // load dates of whole month
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        list.add(calendar.time)
        while (currentMonth == calendar[Calendar.MONTH]) {
            calendar.add(Calendar.DATE, +1)
            if (calendar[Calendar.MONTH] == currentMonth)
                list.add(calendar.time)
        }
        calendar.add(Calendar.DATE, -1)
        return list
    }
}