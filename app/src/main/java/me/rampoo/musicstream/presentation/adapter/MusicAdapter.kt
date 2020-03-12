package me.rampoo.musicstream.presentation.adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.rampoo.musicstream.R
import me.rampoo.musicstream.baseactivity.MusicPlayerActivity
import me.rampoo.musicstream.data.model.Music

class MusicAdapter(val musicList: ArrayList<Music>, val context :Context) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.music_card, parent  ,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: MusicAdapter.ViewHolder, position: Int) {

        val music:Music = musicList[position]
        val pos = position

        holder.loadUrl(music.image)

        holder.titleTv.setText(music.name)

        holder.itemView.setOnClickListener {
            val intent = Intent(context , MusicPlayerActivity::class.java)
            intent.putExtra("position" , pos)
            intent.putExtra("playlist", musicList)
//            intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT)
            context.startActivity(intent)
        }

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val imageView = itemView.findViewById(R.id.roundImage) as ImageView
        val titleTv = itemView.findViewById(R.id.tvSongName) as TextView

        fun loadUrl(url:String){
            Picasso.get().load(url).resize(100, 100)
                .centerCrop().into(imageView)
        }
    }
}

