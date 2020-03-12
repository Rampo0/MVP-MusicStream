package me.rampoo.musicstream.baseactivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_dashboard.*
import me.rampoo.musicstream.R
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.domain.model.MusicApi
import me.rampoo.musicstream.presentation.adapter.MusicAdapter
import me.rampoo.musicstream.presentation.repository.IMusicView

class DashboardActivity : AppCompatActivity(), IMusicView {

    val context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        recycleViewMusic.layoutManager = LinearLayoutManager(this)

        val musicApi = MusicApi(this)
        musicApi.Retrieve()

    }

    override fun onRetriveResult(musics: ArrayList<Music>) {
        val adapter = MusicAdapter(musics , context)
        recycleViewMusic.adapter = adapter
    }

    override fun onRetrieveError(messages: String) {
        Toast.makeText(context , messages , Toast.LENGTH_LONG).show()
    }
}
