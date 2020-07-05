package com.sample.skilltest.details.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sample.skilltest.databinding.ItemCommentBinding
import com.sample.skilltest.details.model.CommentData

class CommentsAdapter(private val commentData: List<CommentData>) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    class ViewHolder(itemView: ItemCommentBinding) : RecyclerView.ViewHolder(itemView.root) {
        val commentView: TextView = itemView.txtComment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCommentBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.commentView.text = commentData[position].comment
    }
}