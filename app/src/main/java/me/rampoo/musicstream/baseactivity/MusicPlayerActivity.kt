package me.rampoo.musicstream.baseactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_music_player.*
import me.rampoo.musicstream.R
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.domain.model.MusicPlayer
import me.rampoo.musicstream.presentation.repository.IMusicPlayerView
import java.lang.Exception

class MusicPlayerActivity : AppCompatActivity() , IMusicPlayerView {

    var currPos: Int = 0
    lateinit var currPlaylist: ArrayList<Music>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        currPos = (intent.getSerializableExtra("position") as? Int)!!
        currPlaylist = (intent.getSerializableExtra("playlist") as? ArrayList<Music>)!!

        MusicPlayer.SetPlaylist(currPlaylist)
        MusicPlayer.SetIView(this)
        MusicPlayer.Play(currPos)

        buttonPlay.setOnClickListener {
            if (MusicPlayer.isPause){
                MusicPlayer.Resume()
            }else{
                MusicPlayer.Pause()
            }
            MusicPlayer.isPause = !MusicPlayer.isPause
        }

        buttonNext.setOnClickListener {
            MusicPlayer.Next()
        }

        buttonPrevious.setOnClickListener {
            MusicPlayer.Prev()
        }

    }

    override fun onPlay(music: Music) {
        textSong.setText(music.name)
        textArtist.setText(music.artist)
//        Picasso.get()
//            .load(music.image)
//            .networkPolicy(NetworkPolicy.OFFLINE)
////            .memoryPolicy(MemoryPolicy.NO_CACHE)
//            .placeholder(R.drawable.albumart)
//            .centerCrop()
//            .resize(200, 260)
////            .error(R.drawable.albumart)
//
//            .into(albumArt, object: com.squareup.picasso.Callback{
//                override fun onSuccess() {
//
//                }
//
//                override fun onError(e: Exception?) {
//
//                }
//            });

    }
}
