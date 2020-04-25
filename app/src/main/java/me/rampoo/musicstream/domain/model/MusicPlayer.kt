package me.rampoo.musicstream.domain.model

import android.media.AudioManager
import android.media.MediaPlayer
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.domain.repository.IMusicPlayer
import me.rampoo.musicstream.presentation.repository.IMusicPlayerView

object MusicPlayer : IMusicPlayer {

    val mediaPlayer : MediaPlayer? = MediaPlayer().apply {
        setAudioStreamType(AudioManager.STREAM_MUSIC)
    }
    lateinit var currPlaylist : ArrayList<Music>
    var currPos : Int = -1

    lateinit var iMusicPlayerView: IMusicPlayerView
    var isPause = true
    var started = false

    init {
        mediaPlayer!!.setOnPreparedListener {
            it.start()
        }
    }

    override fun Play(pos : Int) {
        if(currPos != pos){
            // play new song
            currPos = pos
            val currMusic = currPlaylist[currPos]
            isPause = false
            started = true

            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(currMusic.song_file)
            mediaPlayer!!.prepareAsync()
        }
    }

    fun GetNowPlaying(): Music {
        return currPlaylist[currPos]
    }

    override fun Next() {
        var newPos = currPos + 1

        if (newPos == currPlaylist.size){
            newPos = 0
        }
        Play(newPos)
    }

    override fun Prev() {
        var newPos = currPos - 1
        if(newPos < 0){
            newPos = currPlaylist.size - 1
        }
        Play(newPos)
    }

    override fun Pause() {
        if (mediaPlayer!!.isPlaying()){
            mediaPlayer.pause()
            isPause = true
        }
    }

    override fun Resume() {
        if(!mediaPlayer!!.isPlaying() && started == true){
            mediaPlayer.start()
            isPause = false
        }else{
            started = true
            isPause = false
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(currPlaylist[currPos].song_file)
            mediaPlayer!!.prepareAsync()
        }
    }

    override fun SetIView(view: IMusicPlayerView) {
        iMusicPlayerView = view
    }

    override fun SetMusicForView(music: Music) {
        iMusicPlayerView.onPlay(music)
    }

    override fun SetPlaylist(playlist: ArrayList<Music>) {
        currPlaylist = playlist
        currPos = 0
    }

}