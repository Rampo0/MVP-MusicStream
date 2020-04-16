package me.rampoo.musicstream.baseactivity

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.title_action_bar.view.*

import me.rampoo.musicstream.R
import me.rampoo.musicstream.data.model.Artist
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.domain.model.ArtistApi
import me.rampoo.musicstream.domain.model.MusicApi
import me.rampoo.musicstream.presentation.adapter.ArtistAdapter
import me.rampoo.musicstream.presentation.adapter.MusicAdapter
import me.rampoo.musicstream.presentation.repository.IArtistView
import me.rampoo.musicstream.presentation.repository.IMusicView
import kotlinx.android.synthetic.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() , IMusicView , IArtistView{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var song_recycler_view : RecyclerView
    lateinit var artist_recycler_view : RecyclerView
    lateinit var musicApi : MusicApi
    lateinit var artistApi: ArtistApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        musicApi = MusicApi(this)
        musicApi.Retrieve()

        artistApi = ArtistApi(this)
        artistApi.ArtistRetrieve()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater!!.inflate(R.layout.fragment_home, container, false)
        val handler = Handler()
        var runnable : Runnable

        view.title_action_bar.setText("Home")
        song_recycler_view = view.recycler_view as RecyclerView
//        song_recycler_view.layoutManager = LinearLayoutManager(activity)

//        view.recycler_view_playlist.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false)
        artist_recycler_view = view.recycler_view_playlist as RecyclerView
//        artist_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false)
//        artist_recycler_view.layoutManager = LinearLayoutManager(context)


        view.swipe_refresh_layout.setOnRefreshListener {
            runnable = Runnable {

                musicApi.Retrieve()
                artistApi.ArtistRetrieve()

                view.swipe_refresh_layout.isRefreshing = false
            }

            handler.postDelayed(
                runnable,
                1000
            )
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onRetriveResult(musics: ArrayList<Music>) {
         val adapter = activity?.let { MusicAdapter(musics , it) }
         song_recycler_view.adapter = adapter
    }

    override fun onRetrieveError(messages: String) {
        Toast.makeText(context , messages , Toast.LENGTH_LONG).show()
    }

    override fun onRetriveArtistResult(artists: ArrayList<Artist>) {
        val adapter = activity?.let { ArtistAdapter(artists , it) }
        artist_recycler_view.adapter = adapter
    }

    override fun onRetrieveArtistError(messages: String) {
        Toast.makeText(context , messages , Toast.LENGTH_LONG).show()
    }
}

