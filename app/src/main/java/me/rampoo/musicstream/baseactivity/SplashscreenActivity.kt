package me.rampoo.musicstream.baseactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import me.rampoo.musicstream.R

class SplashscreenActivity : Activity() {

    lateinit var handler: Handler
    val delay = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this , DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }, delay.toLong())

    }
}
