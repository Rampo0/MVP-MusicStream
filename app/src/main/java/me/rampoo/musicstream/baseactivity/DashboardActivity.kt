package me.rampoo.musicstream.baseactivity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import me.rampoo.musicstream.R
import me.rampoo.musicstream.domain.model.CreateNotification
import me.rampoo.musicstream.domain.model.MusicPlayer
import java.util.*


class DashboardActivity : AppCompatActivity() {

    val context : Context = this
    lateinit var memorizePage : Stack<Int>
    val HOME_FRAG_TAG = "HomeTag"
    val LIB_FRAG_TAG = "LibTag"
    val SEARCH_FRAG_TAG = "SearchTag"
    lateinit var currentNavTAG : String
    lateinit var connectionHandler: Handler
    var connectFlag = false
    lateinit var notificationManager: NotificationManager

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

        connectionHandler = Handler()
        connectionCheck()
        createChannel()

    }

    private fun createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CreateNotification.CHANNEL_ID,
                "Music App" , NotificationManager.IMPORTANCE_LOW)

            notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    private fun connectionCheck(){
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if(isConnected){
            no_inet_layout.visibility = View.INVISIBLE
            fragment_main.visibility = View.VISIBLE

            if(connectFlag){
                connectFlag = false
                val intent = Intent(this, DashboardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
                startActivity(intent)
                finish()
            }

        }else{
            fragment_main.visibility = View.INVISIBLE
            no_inet_layout.visibility = View.VISIBLE
            connectFlag = true
            MusicPlayer.Stop()
        }

        val runnable = Runnable {
            this.connectionCheck()
        }
        connectionHandler.postDelayed(runnable , 1000)
    }

    private fun Initalize(){
        currentNavTAG = ""
        memorizePage = Stack<Int>()
        switchFragment(HOME_FRAG_TAG,1)
        memorizePage.push(0)

        bottom_navigation_view.setOnNavigationItemSelectedListener (OnNavigationItemSelectListener)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        handleNavOnBackPressed()
    }

    private fun handleNavOnBackPressed(){

        if(memorizePage.size > 0){
            memorizePage.pop()
            if(memorizePage.size > 0) {
                val prevMenu = memorizePage.peek()
                if (prevMenu != null) {
                    bottom_navigation_view.menu.getItem(prevMenu).setChecked(true)
                    when (prevMenu) {
                        0 -> {
                            switchFragment(HOME_FRAG_TAG, 0)
                        }
                        1 -> {
                            switchFragment(LIB_FRAG_TAG, 0)
                        }
                        2 -> {
                            switchFragment(SEARCH_FRAG_TAG, 0)
                        }
                    }
                }
            }
        }
    }

    public fun globalSwitchFragment(tag : String, flag : Int){
        switchFragment(tag, flag)
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
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                            .show(supportFragmentManager.findFragmentByTag(HOME_FRAG_TAG)!!).commit()
                        if(flag == 1) supportFragmentManager.beginTransaction().addToBackStack(null).commit()
                    }else{

                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                            .add(R.id.fragment_main, HomeFragment(), HOME_FRAG_TAG).commit()
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
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                            .show(supportFragmentManager.findFragmentByTag(LIB_FRAG_TAG)!!).commit()
                        if(flag == 1) supportFragmentManager.beginTransaction().addToBackStack(null).commit()
                    }else{
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                            .add(R.id.fragment_main, LibraryFragment(), LIB_FRAG_TAG).commit()
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
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                            .show(supportFragmentManager.findFragmentByTag(SEARCH_FRAG_TAG)!!).commit()
                        if(flag == 1) supportFragmentManager.beginTransaction().addToBackStack(null).commit()
                    }else{
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                            .add(R.id.fragment_main, SearchFragment(), SEARCH_FRAG_TAG).commit()
                        if(flag == 1) supportFragmentManager.beginTransaction().addToBackStack(null).commit()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager.cancelAll()
        }
    }
}
