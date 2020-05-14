package me.rampoo.musicstream.domain.model

import android.media.AudioManager
import android.media.MediaPlayer
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.domain.repository.IMusicPlayer
import me.rampoo.musicstream.presentation.repository.IMusicPlayerView

object MusicPlayer : IMusicPlayer, MediaPlayer.OnPreparedListener {

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
            it.setOnPreparedListener(this)
        }

        mediaPlayer!!.setOnCompletionListener {
            Next()
        }
    }

    override fun Play(pos : Int) {
        if(currPos != pos || !started){
            // play new song
            currPos = pos
            val currMusic = currPlaylist[currPos]
            isPause = false
            started = true

            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(currMusic.song_file)
            mediaPlayer!!.prepareAsync()
            iMusicPlayerView.onPlay(currMusic)

        }
    }

    fun Stop(){
        isPause = true
        started = false
        val currMusic = currPlaylist[currPos]
        mediaPlayer!!.reset()
        mediaPlayer!!.setDataSource(currMusic.song_file)
        mediaPlayer!!.prepareAsync()
    }

    fun GetNowPlaying(): Music {
        return currPlaylist[currPos]
    }

    fun getDuration() : Int {
        return mediaPlayer!!.duration
    }

    fun getCurrentPosition() : Int{
        return mediaPlayer!!.currentPosition
    }

    fun isPlaying(): Boolean{
        return mediaPlayer!!.isPlaying
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
            iMusicPlayerView.onMusicPause()
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
        iMusicPlayerView.onMusicResume()
    }

    override fun SetIView(view: IMusicPlayerView) {
        iMusicPlayerView = view
    }

    override fun SetMusicForView(music: Music) {
        iMusicPlayerView.onPlay(music)
    }

    fun prepareMediaPlayerOnGetPlaylist(){
        val currMusic = currPlaylist[currPos]
        mediaPlayer!!.reset()
        mediaPlayer!!.setDataSource(currMusic.song_file)
        mediaPlayer!!.prepareAsync()
    }

    override fun SetPlaylist(playlist: ArrayList<Music>) {
        currPlaylist = playlist
        currPos = 0
        prepareMediaPlayerOnGetPlaylist()
    }

    override fun SeekTo(progress: Int) {
        mediaPlayer?.seekTo(progress)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        if(started){
            mp?.start()
        }

        iMusicPlayerView.onPrepared(mp)
    }

}