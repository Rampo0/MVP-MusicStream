package me.rampoo.musicstream.baseactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_library.view.*
import kotlinx.android.synthetic.main.title_action_bar.view.*

import me.rampoo.musicstream.R
import me.rampoo.musicstream.data.model.Artist
import me.rampoo.musicstream.domain.model.ArtistApi
import me.rampoo.musicstream.presentation.adapter.BigArtistAdapter
import me.rampoo.musicstream.presentation.repository.IArtistView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LibraryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LibraryFragment : Fragment() , IArtistView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var myrecycler_view : RecyclerView
    lateinit var artistsApi: ArtistApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        artistsApi = ArtistApi(this)
        artistsApi.ArtistRetrieve()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater!!.inflate(R.layout.fragment_library, container, false)
        view.title_action_bar.setText("Library")

        view.refresh_front.setOnClickListener {
            artistsApi.ArtistRetrieve()
        }

        myrecycler_view = view.gridrecycler_view as RecyclerView

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LibraryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LibraryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onRetriveArtistResult(artists: ArrayList<Artist>) {
        val adapter = activity?.let {BigArtistAdapter(artists , it) }
        myrecycler_view.adapter = adapter
    }

    override fun onRetrieveArtistError(messages: String) {
        Toast.makeText(context , messages , Toast.LENGTH_LONG).show()
    }
}
