package com.cs473.foodiepal.TabMealPlanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.cs473.foodiepal.R
import com.cs473.foodiepal.databinding.FragmentMealPlannerBinding
import com.cs473.foodiepal.TabRecipes.UserData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class MealFragment : Fragment(), OnDayClickListener, OnSelectDateListener, MealDialog.onSavePlanListener  {
    private lateinit var binding: FragmentMealPlannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_meal_planner, container, false)
        binding = FragmentMealPlannerBinding.bind(view)

        binding.calendarView.setOnDayClickListener(this)
        binding.btnAdd.setOnClickListener{onAdd()}
        updateCalendar()
        updateSelectedToday(Date())

        return binding.root
    }

    private fun updateCalendar() {
        var mealDates: MutableList<EventDay> = ArrayList()

        UserData.userAccount.mealPlans.forEach{
            val cal = Calendar.getInstance()
            cal.time = it.date
            mealDates.add(EventDay(cal, android.R.drawable.star_big_on))
        }
        binding.calendarView.setEvents(mealDates)
    }

    private fun updateMealPlanned(date: Date) {
        val selectedPlan = UserData.userAccount.mealPlans.find{it.date == date}
        updateMenu(binding.tvMenuBreakfast, binding.ivMenuBreakfast,
            selectedPlan?.mealID?.get(0) ?: 0
        )
        updateMenu(binding.tvMenuLunch, binding.ivMenuLunch,
            selectedPlan?.mealID?.get(1) ?: 0
        )
        updateMenu(binding.tvMenuDinner, binding.ivMenuDinner,
            selectedPlan?.mealID?.get(2) ?: 0
        )
    }

    private fun updateMenu(tv: TextView, im: ImageView, mealID: Int){
        val recipe = UserData.userAccount.recipes.find{it.id == mealID}
        tv.text = ""
        im.setImageResource(0)
        if(recipe == null)
            return
        tv.text = recipe.name
        im.setImageResource(recipe.imgId)
    }

    private fun updateSelectedToday(date: Date){
        val currentDayString = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(date)
        binding.tvSelectedDay.text = currentDayString

        updateMealPlanned(Date(currentDayString))
    }

    private fun onAdd() {
        val date = Date(binding.tvSelectedDay.text.toString())
        var selectedPlan = UserData.userAccount.mealPlans.find { it.date == Date(binding.tvSelectedDay.text.toString())}
        if(selectedPlan == null)
            selectedPlan = MealPlan(date, mutableListOf())

        val dialog = MealDialog(selectedPlan)
        dialog.setTargetFragment(this,0)
        dialog.show(parentFragmentManager, MealDialog::class.java.name)

    }

    override fun onDayClick(eventDay: EventDay) {
        updateSelectedToday(eventDay.calendar.time)
        //binding.calendarView.setDate(eventDay.calendar)
        binding.calendarView.setHighlightedDays(mutableListOf(eventDay.calendar))

    }

    override fun onSelect(calendar: List<Calendar>) {
        // open AddNoteActivity
        //val num = calendar.size
    }

    override fun onSavePlan(planDate: Date) {
        updateSelectedToday(planDate)
        val cal = Calendar.getInstance()
        cal.time = planDate
        binding.calendarView.setHighlightedDays(mutableListOf(cal))
        updateCalendar()
    }
}