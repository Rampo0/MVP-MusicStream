package me.rampoo.musicstream.presentation.adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import me.rampoo.musicstream.R
import me.rampoo.musicstream.baseactivity.MusicPlayerActivity
import me.rampoo.musicstream.data.model.Artist
import me.rampoo.musicstream.data.model.Music
import org.w3c.dom.Text

class BigArtistAdapter(val artistList: ArrayList<Artist>, val context :Context) : RecyclerView.Adapter<BigArtistAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BigArtistAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist_child, parent  ,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return artistList.size
    }

    override fun onBindViewHolder(holder: BigArtistAdapter.ViewHolder, position: Int) {

        holder.playlistImageIv.setImageDrawable(ContextCompat.getDrawable(context , R.drawable.default_image_round))
        holder.playlistTitleTv.setText(artistList[position].artist_name)

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val playlistTitleTv = itemView.findViewById(R.id.title) as TextView
        val playlistImageIv = itemView.findViewById(R.id.image) as ImageView
    }
}

