package me.rampoo.musicstream.baseactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_music_player.*
import me.rampoo.musicstream.R
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.domain.model.MusicPlayer

class MusicPlayerActivity : AppCompatActivity() {

    var currPos: Int = 0
    lateinit var currPlaylist: ArrayList<Music>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        currPos = (intent.getSerializableExtra("position") as? Int)!!
        currPlaylist = (intent.getSerializableExtra("playlist") as? ArrayList<Music>)!!

        MusicPlayer.SetPlaylist(currPlaylist)
        MusicPlayer.Play(currPos)

        var isPause = false

        playBtn.setOnClickListener {
            if (isPause){
                MusicPlayer.Resume()
            }else{
                MusicPlayer.Pause()
            }
            isPause = !isPause
        }

        nextBtn.setOnClickListener {
            MusicPlayer.Next()
        }

        prevBtn.setOnClickListener {
            MusicPlayer.Prev()
        }

    }
}
