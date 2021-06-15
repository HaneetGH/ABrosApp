package com.technorapper.hiltsample.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import com.technorapper.hiltsample.R
import com.technorapper.hiltsample.data.model.RequestPost
import com.technorapper.hiltsample.databinding.TextPostCellBinding
import com.technorapper.hiltsample.databinding.VideoPostCellBinding
import com.technorapper.hiltsample.utils.RecyclerViewClickListener

class DataListAdapter(
    private var list: ArrayList<RequestPost>?,
    private var listener: RecyclerViewClickListener
) : RecyclerView.Adapter<ViewHolder>() {
    var onEndOfListReached: (() -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        when (viewType) {
            1 -> {
                val binding = DataBindingUtil.inflate<TextPostCellBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.text_post_cell,
                    parent,
                    false
                )
                return TextViewHolder(binding)
            }
            2 -> {
                val binding = DataBindingUtil.inflate<VideoPostCellBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.video_post_cell,
                    parent,
                    false
                )
                return VideoViewHolder(binding)
            }


            else -> {
                val binding = DataBindingUtil.inflate<TextPostCellBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.text_post_cell,
                    parent,
                    false
                )
                return TextViewHolder(binding)
            }

        }


    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        if (position == list!!.size - 1) {
            onEndOfListReached?.invoke()
        }
        when (getItemViewType(position)) {

            1 -> {

                val holder = viewHolder as TextViewHolder

                holder.binding.model = list!![position]

            }
            2 -> {

                val holder = viewHolder as VideoViewHolder
                holder.binding.model = list!![position]
            }


            else -> {
                val holder = viewHolder as TextViewHolder
                holder.binding.model = list!![position]
            }

        }


    }


    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return when (list!![position].type) {
            "Video" -> 2
            "video" -> 2
            else -> 1
        }
    }


    inner class TextViewHolder(private val mBinding: TextPostCellBinding) :
        ViewHolder(mBinding.root) {
        val binding = mBinding

    }


    inner class VideoViewHolder(private val mBinding: VideoPostCellBinding) :
        ViewHolder(mBinding.root) {
        val binding = mBinding

    }


}