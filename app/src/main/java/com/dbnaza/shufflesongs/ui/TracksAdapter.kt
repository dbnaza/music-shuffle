package com.dbnaza.shufflesongs.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dbnaza.shufflesongs.R
import com.dbnaza.shufflesongs.model.Track
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_track.view.*

class TracksAdapter : ListAdapter<Track, TracksAdapter.TrackViewHolder>(DIFF_CALLBACK) {

    class TrackViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(track: Track) {
            with(containerView) {
                textTrackName.text = track.name
                textArtistName.text = track.artistName

                Glide
                    .with(containerView.context)
                    .load(track.cover)
                    .centerCrop()
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageTrackCover)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_track, parent, false)

        return TrackViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DIFF_CALLBACK : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }

}