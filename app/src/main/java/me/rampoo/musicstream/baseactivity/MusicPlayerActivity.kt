package me.rampoo.musicstream.baseactivity

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_music_player.*
import me.rampoo.musicstream.R
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.domain.model.MusicPlayer
import me.rampoo.musicstream.presentation.repository.IMusicPlayerView
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MusicPlayerActivity : AppCompatActivity() , IMusicPlayerView {

    var currPos: Int = 0
    lateinit var currPlaylist: ArrayList<Music>
    lateinit var seekBarHandler: Handler
    var onTrackSeekbar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        currPos = (intent.getSerializableExtra("position") as? Int)!!
        currPlaylist = (intent.getSerializableExtra("playlist") as? ArrayList<Music>)!!

        MusicPlayer.SetIView(this)
        MusicPlayer.SetMusicForView(MusicPlayer.GetNowPlaying())

        if (!MusicPlayer!!.isPause){
            buttonPlay.setImageResource(R.drawable.ic_pause_white)
        }else{
            buttonPlay.setImageResource(R.drawable.ic_play)
        }

        val duration = MusicPlayer.getDuration()
        processUIforDuration(duration)

        seekBarHandler = Handler()
        seekBarProcess()

        playerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    processUIforCurrentTime(progress)
                    MusicPlayer.SeekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                onTrackSeekbar = true
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                onTrackSeekbar = false
                MusicPlayer.SeekTo(seekBar!!.progress)
            }
        })

        buttonPlay.setOnClickListener {
            if (MusicPlayer.isPause){
                MusicPlayer.Resume()
            }else{
                MusicPlayer.Pause()
            }
        }

        buttonNext.setOnClickListener {
            MusicPlayer.Next()
            MusicPlayer.SetMusicForView(MusicPlayer.GetNowPlaying())
        }

        buttonPrevious.setOnClickListener {
            MusicPlayer.Prev()
            MusicPlayer.SetMusicForView(MusicPlayer.GetNowPlaying())
        }
    }

    fun getMinuteFormat(duration : Int) : String{
        return String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration.toLong()))
        )
    }

    fun processUIforCurrentTime(currentPosition : Int){
        playerSeekBar.setProgress(currentPosition)
        textCurrentTime.setText(getMinuteFormat(currentPosition))
    }

    fun seekBarProcess(){

        if(!onTrackSeekbar) {
            val currentPosition = MusicPlayer.getCurrentPosition()
            processUIforCurrentTime(currentPosition)
        }
        val runnable = Runnable {
            this.seekBarProcess()
        }

        seekBarHandler.postDelayed(runnable,1000)
    }

    override fun onPlay(music: Music) {
        textSong.setText(music.name)
        textArtist.setText(music.artist)
        buttonPlay.setImageResource(R.drawable.ic_pause_white)
        processUIforCurrentTime(0)
    }

    override fun onMusicPause() {
        buttonPlay.setImageResource(R.drawable.ic_play)
    }

    override fun onMusicResume() {
        buttonPlay.setImageResource(R.drawable.ic_pause_white)
    }

    fun processUIforDuration(duration : Int){
        playerSeekBar.max = duration
        textEndTime.setText(getMinuteFormat(duration))
    }

    override fun onPrepared(mp: MediaPlayer?) {
        processUIforDuration(mp!!.getDuration())
    }
}
