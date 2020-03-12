package me.rampoo.musicstream.domain.model

import android.media.AudioManager
import android.media.MediaPlayer
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.domain.repository.IMusicPlayer

object MusicPlayer : IMusicPlayer {

    val mediaPlayer : MediaPlayer? = MediaPlayer().apply {
        setAudioStreamType(AudioManager.STREAM_MUSIC)
    }
    lateinit var currPlaylist : ArrayList<Music>
    var currPos : Int = 0

    init {
        mediaPlayer!!.setOnPreparedListener {
            it.start()
        }
    }

    override fun Play(pos : Int) {

        if(currPos != pos){
            // play new song
            currPos = pos
            var currMusic = currPlaylist[currPos]

            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(currMusic.song_file)
            mediaPlayer!!.prepareAsync()
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

    override fun SetPlaylist(playlist: ArrayList<Music>) {
        currPlaylist = playlist
    }

}