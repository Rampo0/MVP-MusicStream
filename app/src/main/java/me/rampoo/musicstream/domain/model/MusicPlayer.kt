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
    var isPause = false

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

            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(currMusic.song_file)
            mediaPlayer!!.prepareAsync()
        }

        if(iMusicPlayerView != null){
            val currMusic = currPlaylist[currPos]
            iMusicPlayerView.onPlay(currMusic)
        }

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
        }
    }

    override fun Resume() {
        if(!mediaPlayer!!.isPlaying()){
            mediaPlayer.start()
        }
    }

    override fun SetIView(view: IMusicPlayerView) {
        iMusicPlayerView = view
    }

    override fun SetPlaylist(playlist: ArrayList<Music>) {
        currPlaylist = playlist
    }

}