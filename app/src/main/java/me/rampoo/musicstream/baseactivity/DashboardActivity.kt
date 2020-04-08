package me.rampoo.musicstream.baseactivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import me.rampoo.musicstream.R
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.domain.model.MusicApi
import me.rampoo.musicstream.presentation.adapter.MusicAdapter
import me.rampoo.musicstream.presentation.repository.IMusicView
import java.util.*
import kotlin.collections.ArrayList

class DashboardActivity : AppCompatActivity(), IMusicView {

    val context : Context = this
    lateinit var memorizePage : Stack<Int>
    val HOME_FRAG_TAG = "HomeTag"
    val LIB_FRAG_TAG = "LibTag"
    val SEARCH_FRAG_TAG = "SearchTag"
    lateinit var currentNavTAG : String

    private val OnNavigationItemSelectListener = BottomNavigationView.OnNavigationItemSelectedListener{
        when(it.itemId){
            R.id.navigation_home -> {
                switchFragment(HOME_FRAG_TAG,1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_library -> {
                switchFragment(LIB_FRAG_TAG,1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                switchFragment(SEARCH_FRAG_TAG, 1)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        // Initialize
        Initalize()

        // recycleViewMusic.layoutManager = LinearLayoutManager(this)
        // val musicApi = MusicApi(this)
        // musicApi.Retrieve()

    }

    private fun Initalize(){
        currentNavTAG = HOME_FRAG_TAG
        memorizePage = Stack<Int>()
        memorizePage.push(0)
        supportFragmentManager.beginTransaction().add(R.id.fragment_main , HomeFragment()).commit()
        bottom_navigation_view.setOnNavigationItemSelectedListener (OnNavigationItemSelectListener)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        handleNavOnBackPressed()
    }

    private fun handleNavOnBackPressed(){
        memorizePage.pop()
        if(memorizePage.size > 0){
            val prevMenu = memorizePage.peek()
            if(prevMenu != null){
                bottom_navigation_view.menu.getItem(prevMenu).setChecked(true)
                when(prevMenu){
                    0 -> {
                        switchFragment(HOME_FRAG_TAG,0)
                    }
                    1 -> {
                        switchFragment(LIB_FRAG_TAG,0)
                    }
                    2 -> {
                        switchFragment(SEARCH_FRAG_TAG,0)
                    }
                }
            }
        }
    }

    private fun switchFragment(tag : String, flag : Int){

        when(tag){
            HOME_FRAG_TAG -> {
                if(currentNavTAG != HOME_FRAG_TAG){

                    currentNavTAG = HOME_FRAG_TAG
                    if(flag == 1) memorizePage.push(0)

                    if(supportFragmentManager.findFragmentByTag(SEARCH_FRAG_TAG) != null){
                        supportFragmentManager.beginTransaction().hide(supportFragmentManager.findFragmentByTag(SEARCH_FRAG_TAG)!!).commit()
                    }
                    if(supportFragmentManager.findFragmentByTag(LIB_FRAG_TAG) != null){
                        supportFragmentManager.beginTransaction().hide(supportFragmentManager.findFragmentByTag(LIB_FRAG_TAG)!!).commit()
                    }

                    if(supportFragmentManager.findFragmentByTag(HOME_FRAG_TAG) != null){
                        supportFragmentManager.beginTransaction().show(supportFragmentManager.findFragmentByTag(HOME_FRAG_TAG)!!).commit()
                        if(flag == 1) supportFragmentManager.beginTransaction().addToBackStack(null).commit()
                    }else{
                        supportFragmentManager.beginTransaction().add(R.id.fragment_main, HomeFragment(), HOME_FRAG_TAG).commit()
                        if(flag == 1) supportFragmentManager.beginTransaction().addToBackStack(null).commit()
                    }
                }
            }
            LIB_FRAG_TAG -> {
                if(currentNavTAG != LIB_FRAG_TAG){

                    currentNavTAG = LIB_FRAG_TAG
                    if(flag == 1) memorizePage.push(1)

                    if(supportFragmentManager.findFragmentByTag(SEARCH_FRAG_TAG) != null){
                        supportFragmentManager.beginTransaction().hide(supportFragmentManager.findFragmentByTag(SEARCH_FRAG_TAG)!!).commit()
                    }
                    if(supportFragmentManager.findFragmentByTag(HOME_FRAG_TAG) != null){
                        supportFragmentManager.beginTransaction().hide(supportFragmentManager.findFragmentByTag(HOME_FRAG_TAG)!!).commit()
                    }

                    if(supportFragmentManager.findFragmentByTag(LIB_FRAG_TAG) != null){
                        supportFragmentManager.beginTransaction().show(supportFragmentManager.findFragmentByTag(LIB_FRAG_TAG)!!).commit()
                        if(flag == 1) supportFragmentManager.beginTransaction().addToBackStack(null).commit()
                    }else{
                        supportFragmentManager.beginTransaction().add(R.id.fragment_main, LibraryFragment(), LIB_FRAG_TAG).commit()
                        if(flag == 1) supportFragmentManager.beginTransaction().addToBackStack(null).commit()
                    }
                }
            }
            SEARCH_FRAG_TAG -> {

                if(currentNavTAG != SEARCH_FRAG_TAG){

                    currentNavTAG = SEARCH_FRAG_TAG
                    if(flag == 1) memorizePage.push(2)

                    if(supportFragmentManager.findFragmentByTag(LIB_FRAG_TAG) != null){
                        supportFragmentManager.beginTransaction().hide(supportFragmentManager.findFragmentByTag(LIB_FRAG_TAG)!!).commit()
                    }
                    if(supportFragmentManager.findFragmentByTag(HOME_FRAG_TAG) != null){
                        supportFragmentManager.beginTransaction().hide(supportFragmentManager.findFragmentByTag(HOME_FRAG_TAG)!!).commit()
                    }

                    if(supportFragmentManager.findFragmentByTag(SEARCH_FRAG_TAG) != null){
                        supportFragmentManager.beginTransaction().show(supportFragmentManager.findFragmentByTag(SEARCH_FRAG_TAG)!!).commit()
                        if(flag == 1) supportFragmentManager.beginTransaction().addToBackStack(null).commit()
                    }else{
                        supportFragmentManager.beginTransaction().add(R.id.fragment_main, SearchFragment(), SEARCH_FRAG_TAG).commit()
                        if(flag == 1) supportFragmentManager.beginTransaction().addToBackStack(null).commit()
                    }
                }
            }
        }
    }

    override fun onRetriveResult(musics: ArrayList<Music>) {
        // val adapter = MusicAdapter(musics , context)
        // recycleViewMusic.adapter = adapter
    }

    override fun onRetrieveError(messages: String) {
        // Toast.makeText(context , messages , Toast.LENGTH_LONG).show()
    }
}
