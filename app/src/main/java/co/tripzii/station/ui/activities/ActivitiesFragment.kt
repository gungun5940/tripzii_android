package co.tripzii.station.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.tripzii.station.AllTripziiActivity
import co.tripzii.station.R

class ActivitiesFragment : Fragment() {

    private lateinit var activitiesViewModel: ActivitiesViewModel
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activitiesViewModel =
            ViewModelProviders.of(this).get(ActivitiesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_activities, container, false)
        val textView: TextView = root.findViewById(R.id.text_activities)
        activitiesViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}